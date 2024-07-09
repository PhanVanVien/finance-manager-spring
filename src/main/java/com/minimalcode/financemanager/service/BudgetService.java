package com.minimalcode.financemanager.service;

import com.minimalcode.financemanager.dto.other.BudgetRequest;

public interface BudgetService {
    void createBudget(BudgetRequest budgetRequest);
}
