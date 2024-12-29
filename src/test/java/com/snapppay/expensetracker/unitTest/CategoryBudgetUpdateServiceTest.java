package com.snapppay.expensetracker.unitTest;

import com.snapppay.expensetracker.entity.Category;
import com.snapppay.expensetracker.entity.CategoryBudget;
import com.snapppay.expensetracker.entity.User;
import com.snapppay.expensetracker.model.CategoryBudgetRequest;
import com.snapppay.expensetracker.model.CategoryType;
import com.snapppay.expensetracker.repository.CategoryBudgetRepository;
import com.snapppay.expensetracker.service.CategoryBudgetUpdateService;
import com.snapppay.expensetracker.service.CategoryService;
import com.snapppay.expensetracker.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryBudgetUpdateServiceTest {

    @InjectMocks
    private CategoryBudgetUpdateService categoryBudgetUpdateService;
    @Mock
    private CategoryBudgetRepository categoryBudgetRepository;
    @Mock
    private CategoryService categoryService;
    @Mock
    private UserService userService;

    @Test
    public void testSetBudgetSuccess() throws Exception {
        var user = new User();
        user.setId(1L);
        user.setUsername("r.gholamzad");
        when(userService.getUserByUsername(anyString())).thenReturn(user);

        var category = new Category();
        category.setId(1L);
        category.setName(CategoryType.COFFEE.toString());
        when(categoryService.getCategory(any(CategoryType.class))).thenReturn(category);

        when(categoryBudgetRepository.findCategoryBudgetByUserIdAndCategoryId(anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        var budget = new CategoryBudget();
        budget.setId(1L);
        budget.setCategory(category);
        budget.setUser(user);
        budget.setBudget(new BigDecimal(500_000));
        when(categoryBudgetRepository.save(any(CategoryBudget.class))).thenReturn(budget);

        var budgetRequest = new CategoryBudgetRequest(CategoryType.COFFEE, "500000");
        var response = categoryBudgetUpdateService.setCategoryBudget(budgetRequest, "r.gholamzad");

        Assertions.assertEquals(1, response.id());
        Assertions.assertEquals(new BigDecimal(500_000), response.budget());
        verify(userService, times(1)).getUserByUsername("r.gholamzad");
        verify(categoryService, times(1)).getCategory(CategoryType.COFFEE);
        verify(categoryBudgetRepository, times(1)).save(any());
        verify(categoryBudgetRepository, times(1)).findCategoryBudgetByUserIdAndCategoryId(anyLong(), anyLong());
    }

    @Test
    public void testUpdateBudgetSuccess() throws Exception {
        var user = new User();
        user.setId(1L);
        user.setUsername("r.gholamzad");
        when(userService.getUserByUsername(anyString())).thenReturn(user);

        var category = new Category();
        category.setId(1L);
        category.setName(CategoryType.COFFEE.toString());
        when(categoryService.getCategory(any(CategoryType.class))).thenReturn(category);

        var budget = new CategoryBudget();
        budget.setId(2L);
        budget.setCategory(category);
        budget.setUser(user);
        budget.setBudget(new BigDecimal(5000));
        when(categoryBudgetRepository.findCategoryBudgetByUserIdAndCategoryId(anyLong(), anyLong()))
                .thenReturn(Optional.of(budget));

        when(categoryBudgetRepository.save(any(CategoryBudget.class))).thenReturn(budget);

        var budgetRequest = new CategoryBudgetRequest(CategoryType.COFFEE, "2000");
        var response = categoryBudgetUpdateService.setCategoryBudget(budgetRequest, "r.gholamzad");

        Assertions.assertEquals(2, response.id());
        Assertions.assertEquals(new BigDecimal(2000), response.budget());
        verify(userService, times(1)).getUserByUsername("r.gholamzad");
        verify(categoryService, times(1)).getCategory(CategoryType.COFFEE);
        verify(categoryBudgetRepository, times(1)).save(any());
        verify(categoryBudgetRepository, times(1)).findCategoryBudgetByUserIdAndCategoryId(anyLong(), anyLong());
    }
}
