package com.minimalcode.financemanager.service.impl;

import com.minimalcode.financemanager.dto.other.BudgetRequest;
import com.minimalcode.financemanager.exception.BudgetNotFoundException;
import com.minimalcode.financemanager.model.Budget;
import com.minimalcode.financemanager.model.user.User;
import com.minimalcode.financemanager.repository.BudgetRepository;
import com.minimalcode.financemanager.repository.UserRepository;
import com.minimalcode.financemanager.service.BudgetService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void createBudget(BudgetRequest budgetRequest, Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
        Budget budget = Budget.builder()
                .category(budgetRequest.getCategory())
                .amount(budgetRequest.getAmount())
                .spentAmount(BigDecimal.valueOf(0))
                .user(user)
                .build();
        budgetRepository.save(budget);
    }

    @Override
    @Transactional
    public void deleteBudget(Long budgetId) {
        Budget budget = budgetRepository
                .findById(budgetId)
                .orElseThrow(() -> new BudgetNotFoundException("Budget not found with id: " + budgetId));
        budgetRepository.delete(budget);
    }

    @Override
    public List<Budget> getBudgetsByUserId(Long userId, Pageable pageable) {
        userRepository
                .findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userId));

        return budgetRepository.findByUserUserId(userId, pageable).getContent();
    }

    @Override
    @Transactional
    public void editBudget(Long budgetId, BudgetRequest budgetRequest) {
        Budget budget = budgetRepository
                .findById(budgetId)
                .orElseThrow(() -> new BudgetNotFoundException("Budget not found with id: " + budgetId));

        BigDecimal newAmount = budgetRequest.getAmount();
        BigDecimal spentAmount = budget.getSpentAmount();
        if (newAmount != null) {
            if (isValidAmount(newAmount, spentAmount)) {
                budget.setAmount(newAmount);
            } else {
                throw new IllegalArgumentException("New amount must be greater than the spent amount.");
            }
        }

        if (budgetRequest.getCategory() != null && !budgetRequest.getCategory().trim().isEmpty()) {
            budget.setCategory(budgetRequest.getCategory());
        }
        budgetRepository.save(budget);
    }

    public boolean isValidAmount(BigDecimal newAmount, BigDecimal spentAmount) {
        return newAmount.compareTo(spentAmount) > 0;
    }

    @Override
    public Budget getBudgetDetail(Long budgetId) {
        return budgetRepository
                .findById(budgetId)
                .orElseThrow(() -> new BudgetNotFoundException("Budget not found with id: " + budgetId));
    }

    @Override
    public List<String> getDistinctCategoriesByUserId(Long userId) {
        return budgetRepository.findDistinctCategoriesByUserId(userId);
    }

    @Override
    public List<Budget> getBudgetsSortedByDateCreatedDesc(Long userId, Pageable pageable) {
        userRepository
                .findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userId));
        return budgetRepository.findByUserUserId(userId, pageable).getContent();
    }

}
