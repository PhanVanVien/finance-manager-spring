package com.minimalcode.financemanager.controller;

import com.minimalcode.financemanager.dto.other.BudgetRequest;
import com.minimalcode.financemanager.exception.BudgetNotFoundException;
import com.minimalcode.financemanager.model.Budget;
import com.minimalcode.financemanager.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/budget")
public class BudgetController {
    @Autowired
    private BudgetService budgetService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<String> createBudget(
            @Valid @RequestBody BudgetRequest budgetRequest,
            @PathVariable Long userId) {
        try {
            budgetService.createBudget(budgetRequest, userId);
            return ResponseEntity.ok("Budget created successfully");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{budgetId}")
    public ResponseEntity<String> deleteBudget(
            @PathVariable Long budgetId) {
        try {
            budgetService.deleteBudget(budgetId);
            return ResponseEntity.ok("Budget deleted successfully");
        } catch (BudgetNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Budget>> getBudgetsByUsername(
            @PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "0") int pageIndex,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            List<Budget> budgets = budgetService.getBudgetsByUserId(userId, pageable);

            return ResponseEntity.ok(budgets);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
    }

    @PutMapping("/{budgetId}")
    public ResponseEntity<String> editBudget(
            @PathVariable Long budgetId,
            @RequestBody BudgetRequest budgetRequest
    ) {
        try {
            budgetService.editBudget(budgetId, budgetRequest);
            return ResponseEntity.ok("Budget " + budgetId + " updated successfully");
        } catch (BudgetNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/get/{budgetId}")
    public ResponseEntity<?> getBudgetDetail(
            @PathVariable Long budgetId
    ) {
        try {
            Budget budget = budgetService.getBudgetDetail(budgetId);
            return ResponseEntity.status(HttpStatus.OK).body(budget);
        } catch (BudgetNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
