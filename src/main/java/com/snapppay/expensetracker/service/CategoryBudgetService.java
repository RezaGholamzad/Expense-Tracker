package com.snapppay.expensetracker.service;

import com.snapppay.expensetracker.entity.CategoryBudget;
import com.snapppay.expensetracker.model.CategoryType;
import com.snapppay.expensetracker.repository.CategoryBudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryBudgetService {

    private final CategoryBudgetRepository categoryBudgetRepository;
    private final UserService userService;

    public Map<CategoryType, BigDecimal> getCategoryBudget(String username) {
        var user = userService.getUserByUsername(username);
        var budgets = categoryBudgetRepository.findCategoryBudgetByUserId(user.getId());
        return budgets
                .map(categoryBudgets -> categoryBudgets.stream().collect(
                        Collectors.toMap(
                                key -> CategoryType.valueOf(key.getCategory().getName()),
                                CategoryBudget::getBudget)))
                .orElseGet(Map::of);
    }
}
