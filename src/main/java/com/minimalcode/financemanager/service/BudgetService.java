package com.minimalcode.financemanager.service;

import com.minimalcode.financemanager.dto.other.BudgetRequest;
import com.minimalcode.financemanager.model.Budget;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BudgetService {
    void createBudget(BudgetRequest budgetRequest, Long userId);

    void deleteBudget(Long budgetId);

    List<Budget> getBudgetsByUserId(Long userId, Pageable pageable);

    void editBudget(Long budgetId, BudgetRequest budgetRequest);

    Budget getBudgetDetail(Long budgetId);

    List<String> getDistinctCategoriesByUserId(Long userId);

    List<Budget> getBudgetsSortedByDateCreatedDesc(Long userId, Pageable pageable);
}
