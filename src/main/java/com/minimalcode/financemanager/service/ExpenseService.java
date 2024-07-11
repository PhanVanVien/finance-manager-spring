package com.minimalcode.financemanager.service;

import com.minimalcode.financemanager.dto.other.ExpenseRequest;
import com.minimalcode.financemanager.model.Expense;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExpenseService {
    void createExpense(Long budgetId, ExpenseRequest expenseRequest);

    List<Expense> getExpenseByBudgetId(Long budgetId, Pageable pageable);

    Expense getExpenseDetail(Long expenseId);

    void deleteExpense(Long expenseId);
}
