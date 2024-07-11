package com.minimalcode.financemanager.controller;

import com.minimalcode.financemanager.dto.other.ExpenseRequest;
import com.minimalcode.financemanager.exception.BudgetNotFoundException;
import com.minimalcode.financemanager.exception.ExpenseNotFoundException;
import com.minimalcode.financemanager.model.Expense;
import com.minimalcode.financemanager.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {
    private static final String DEFAULT_PAGE_INDEX = "0";
    private static final String DEFAULT_PAGE_SIZE = "10";
    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/create/{budgetId}")
    public ResponseEntity<String> createExpense(
            @PathVariable Long budgetId,
            @Valid @RequestBody ExpenseRequest expenseRequest
    ) {
        try {
            expenseService.createExpense(budgetId, expenseRequest);
            return ResponseEntity.ok("Expense created successfully");
        } catch (BudgetNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{budgetId}")
    public ResponseEntity<?> getExpenseByBudgetId(
            @PathVariable Long budgetId,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_INDEX) int pageIndex,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) int pageSize
    ) {
        try {
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            List<Expense> expenses = expenseService.getExpenseByBudgetId(budgetId, pageable);
            return ResponseEntity.ok(expenses);
        } catch (BudgetNotFoundException e) {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<?> getExpenseDetail(
            @PathVariable Long expenseId
    ) {
        try {
            Expense expense = expenseService.getExpenseDetail(expenseId);
            return ResponseEntity.ok(expense);
        } catch (ExpenseNotFoundException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<String> deleteExpense(
            @PathVariable Long expenseId
    ) {
        try {
            expenseService.deleteExpense(expenseId);
            return ResponseEntity.ok("Expense deleted successfully");
        } catch (ExpenseNotFoundException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    // edit
}
