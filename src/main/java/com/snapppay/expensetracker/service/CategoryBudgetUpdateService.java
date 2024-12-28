package com.snapppay.expensetracker.service;

import com.snapppay.expensetracker.entity.Category;
import com.snapppay.expensetracker.entity.CategoryBudget;
import com.snapppay.expensetracker.entity.User;
import com.snapppay.expensetracker.model.CategoryBudgetRequest;
import com.snapppay.expensetracker.model.CategoryBudgetResponse;
import com.snapppay.expensetracker.repository.CategoryBudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CategoryBudgetUpdateService {

    private final CategoryBudgetRepository categoryBudgetRepository;
    private final CategoryService categoryService;
    private final UserService userService;

    public CategoryBudgetResponse setCategoryBudget(CategoryBudgetRequest request, String username) {
        var user = userService.getUserByUsername(username);
        var category = categoryService.getCategory(request.categoryType());
        var foundedCategoryBudget = categoryBudgetRepository.findCategoryBudgetByUserIdAndCategoryId(user.getId(), category.getId());

        return foundedCategoryBudget.map(categoryBudget -> updateBudget(categoryBudget, request))
                .orElseGet(() -> setBudget(user, category, request));
    }

    private CategoryBudgetResponse updateBudget(CategoryBudget categoryBudget, CategoryBudgetRequest request) {
        categoryBudget.setBudget(new BigDecimal(request.budget()));
        var budgetEntity = categoryBudgetRepository.save(categoryBudget);
        return new CategoryBudgetResponse(budgetEntity.getId(), budgetEntity.getBudget(), request.categoryType());
    }

    private CategoryBudgetResponse setBudget(User user, Category category, CategoryBudgetRequest request) {
        var budgetEntity = new CategoryBudget(category, user, new BigDecimal(request.budget()));
        budgetEntity = categoryBudgetRepository.save(budgetEntity);
        return new CategoryBudgetResponse(budgetEntity.getId(), budgetEntity.getBudget(), request.categoryType());
    }
}
