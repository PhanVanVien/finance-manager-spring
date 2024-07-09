package com.minimalcode.financemanager.service.impl;

import com.minimalcode.financemanager.dto.other.BudgetRequest;
import com.minimalcode.financemanager.model.Budget;
import com.minimalcode.financemanager.repository.BudgetRepository;
import com.minimalcode.financemanager.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BudgetServiceImpl implements BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;

    @Override
    public void createBudget(BudgetRequest budgetRequest) {
        Budget budget = Budget.builder()
                .category(budgetRequest.getCategory())
                .amount(budgetRequest.getAmount())
                .build();
        System.out.println("hello");
        budgetRepository.save(budget);
    }
}
