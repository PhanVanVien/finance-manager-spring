package com.minimalcode.financemanager.service.impl;

import com.minimalcode.financemanager.dto.other.ExpenseRequest;
import com.minimalcode.financemanager.exception.BudgetNotFoundException;
import com.minimalcode.financemanager.exception.ExpenseNotFoundException;
import com.minimalcode.financemanager.model.Budget;
import com.minimalcode.financemanager.model.Expense;
import com.minimalcode.financemanager.repository.BudgetRepository;
import com.minimalcode.financemanager.repository.ExpenseRepository;
import com.minimalcode.financemanager.service.ExpenseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private BudgetRepository budgetRepository;

    @Override
    @Transactional
    public void createExpense(Long budgetId, ExpenseRequest expenseRequest) {
        Budget budget = budgetRepository
                .findById(budgetId)
                .orElseThrow(() -> new BudgetNotFoundException("Budget not found with id: " + budgetId));
        BigDecimal budgetAmount = budget.getAmount();
        BigDecimal spentAmount = budget.getSpentAmount();
        BigDecimal expenseAmount = expenseRequest.getAmount();
        if (spentAmount.add(expenseAmount).compareTo(budgetAmount) > 0) {
            throw new IllegalArgumentException("Expense amount exceeds the available budget.");
        }

        Expense expense = Expense.builder()
                .amount(expenseRequest.getAmount())
                .description(expenseRequest.getDescription())
                .budget(budget)
                .build();

        expenseRepository.save(expense);

        budget.setSpentAmount(spentAmount.add(expenseAmount));
        budgetRepository.save(budget);
    }

    @Override
    public List<Expense> getExpenseByBudgetId(Long budgetId, Pageable pageable) {
        budgetRepository
                .findById(budgetId)
                .orElseThrow(() -> new BudgetNotFoundException("Budget not found with id: " + budgetId));
        return expenseRepository.findByBudgetBudgetId(budgetId, pageable).getContent();
    }

    @Override
    public Expense getExpenseDetail(Long expenseId) {
        return expenseRepository
                .findById(expenseId)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found with id: " + expenseId));
    }

    @Override
    @Transactional
    public void deleteExpense(Long expenseId) {
        Expense expense = expenseRepository
                .findById(expenseId)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found with id: " + expenseId));
        Budget budget = expense.getBudget();
        expenseRepository.delete(expense);
        BigDecimal newSpentAmount = budget.getSpentAmount().subtract(expense.getAmount());
        budget.setSpentAmount(newSpentAmount);
        budgetRepository.save(budget);
    }
}
