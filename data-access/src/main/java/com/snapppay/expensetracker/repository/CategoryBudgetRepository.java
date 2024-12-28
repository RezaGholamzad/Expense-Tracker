package com.snapppay.expensetracker.repository;

import com.snapppay.expensetracker.entity.CategoryBudget;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CategoryBudgetRepository extends CrudRepository<CategoryBudget, Long> {

    Optional<CategoryBudget> findCategoryBudgetByUserIdAndCategoryId(Long userId, Long categoryId);

    Optional<Set<CategoryBudget>> findCategoryBudgetByUserId(Long userId);
}
