package com.minimalcode.financemanager.controller;

import com.minimalcode.financemanager.dto.other.BudgetRequest;
import com.minimalcode.financemanager.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/budgets")
public class BudgetController {
    @Autowired
    private BudgetService budgetService;

    @PostMapping("/budget")
    public ResponseEntity<String> createBudget(@Valid @RequestBody BudgetRequest budgetRequest) {
        budgetService.createBudget(budgetRequest);
        return ResponseEntity.ok("Budget created successfully");
    }
}
