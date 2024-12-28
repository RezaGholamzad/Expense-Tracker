package com.snapppay.expensetracker.unitTest;

import com.snapppay.expensetracker.entity.Category;
import com.snapppay.expensetracker.entity.CategoryBudget;
import com.snapppay.expensetracker.entity.User;
import com.snapppay.expensetracker.model.CategoryTypeDto;
import com.snapppay.expensetracker.repository.CategoryBudgetRepository;
import com.snapppay.expensetracker.service.CategoryBudgetService;
import com.snapppay.expensetracker.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryBudgetServiceTest {

    @InjectMocks
    private CategoryBudgetService categoryBudgetService;
    @Mock
    private CategoryBudgetRepository categoryBudgetRepository;
    @Mock
    private UserService userService;

    @Test
    public void testUserHasCategoryBudget() {
        var user = new User();
        user.setId(1L);
        user.setUsername("r.gholamzad");
        when(userService.getUserEntityByUsername(anyString())).thenReturn(user);

        var category = new Category();
        category.setId(1L);
        category.setName(CategoryTypeDto.COFFEE.toString());
        var categoryBudget = new CategoryBudget();
        categoryBudget.setId(1L);
        categoryBudget.setCategory(category);
        categoryBudget.setBudget(new BigDecimal(100_000));
        when(categoryBudgetRepository.findCategoryBudgetByUserId(1L)).thenReturn(Optional.of(Set.of(categoryBudget)));

        var result = categoryBudgetService.getCategoryBudget("r.gholamzad");

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(new BigDecimal(100_000), result.get(CategoryTypeDto.COFFEE));
        verify(userService, times(1)).getUserEntityByUsername("r.gholamzad");
        verify(categoryBudgetRepository, times(1)).findCategoryBudgetByUserId(1L);
    }

    @Test
    public void testUserDoesNotHaveCategoryBudget() {
        var user = new User();
        user.setId(1L);
        user.setUsername("r.gholamzad");
        when(userService.getUserEntityByUsername(anyString())).thenReturn(user);

        when(categoryBudgetRepository.findCategoryBudgetByUserId(1L)).thenReturn(Optional.of(Set.of()));

        var result = categoryBudgetService.getCategoryBudget("r.gholamzad");

        Assertions.assertEquals(0, result.size());
        verify(userService, times(1)).getUserEntityByUsername("r.gholamzad");
        verify(categoryBudgetRepository, times(1)).findCategoryBudgetByUserId(1L);
    }

}
