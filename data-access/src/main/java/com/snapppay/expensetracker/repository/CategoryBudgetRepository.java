package com.snapppay.expensetracker.repository;

import com.snapppay.expensetracker.entity.CategoryBudget;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryBudgetRepository extends CrudRepository<CategoryBudget, Long> {

    Optional<CategoryBudget> findCategoryBudgetByUserIdAndCategoryId(Long userId, Long categoryId);
}
