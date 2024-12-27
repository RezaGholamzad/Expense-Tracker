package com.snapppay.expensetracker.service;

import com.snapppay.expensetracker.entity.Category;
import com.snapppay.expensetracker.entity.CategoryBudget;
import com.snapppay.expensetracker.entity.User;
import com.snapppay.expensetracker.model.CategoryBudgetDto;
import com.snapppay.expensetracker.model.SavedCategoryBudgetDto;
import com.snapppay.expensetracker.repository.CategoryBudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryBudgetService {

    private final CategoryBudgetRepository categoryBudgetRepository;
    private final CategoryService categoryService;
    private final UserService userService;

    public SavedCategoryBudgetDto setCategoryBudget(CategoryBudgetDto categoryBudgetDto) {
        var user = userService.getUserEntityByUsername(categoryBudgetDto.username());
        var category = categoryService.getCategory(categoryBudgetDto.categoryTypeDto());
        var foundedCategoryBudget = categoryBudgetRepository.findCategoryBudgetByUserIdAndCategoryId(user.getId(), category.getId());

        return foundedCategoryBudget.map(categoryBudget -> updateBudget(categoryBudget, categoryBudgetDto))
                .orElseGet(() -> setBudget(user, category, categoryBudgetDto));
    }

    private SavedCategoryBudgetDto updateBudget(CategoryBudget categoryBudget, CategoryBudgetDto categoryBudgetDto) {
        categoryBudget.setBudget(categoryBudgetDto.budget());
        var budgetEntity = categoryBudgetRepository.save(categoryBudget);
        return new SavedCategoryBudgetDto(categoryBudgetDto, budgetEntity.getId());
    }

    private SavedCategoryBudgetDto setBudget(User user, Category category, CategoryBudgetDto categoryBudgetDto) {
        var budgetEntity = new CategoryBudget(category, user, categoryBudgetDto.budget());
        budgetEntity = categoryBudgetRepository.save(budgetEntity);
        return new SavedCategoryBudgetDto(categoryBudgetDto, budgetEntity.getId());
    }
}
