package com.minimalcode.financemanager.repository;

import com.minimalcode.financemanager.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Page<Expense> findByBudgetBudgetId(Long budgetId, Pageable pageable);
}
