package com.minimalcode.financemanager.repository;

import com.minimalcode.financemanager.model.Budget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Page<Budget> findByUserUserId(Long userId, Pageable pageable);
}
