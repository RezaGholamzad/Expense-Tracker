package com.snapppay.expensetracker.unitTest;

import com.snapppay.expensetracker.entity.Category;
import com.snapppay.expensetracker.entity.CategoryBudget;
import com.snapppay.expensetracker.entity.User;
import com.snapppay.expensetracker.model.CategoryBudgetDto;
import com.snapppay.expensetracker.model.CategoryTypeDto;
import com.snapppay.expensetracker.repository.CategoryBudgetRepository;
import com.snapppay.expensetracker.service.CategoryBudgetService;
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
public class CategoryBudgetServiceTest {

    @InjectMocks
    private CategoryBudgetService categoryBudgetService;
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
        when(userService.getUserEntityByUsername(anyString())).thenReturn(user);

        var category = new Category();
        category.setId(1L);
        category.setName(CategoryTypeDto.COFFEE.toString());
        when(categoryService.getCategory(any(CategoryTypeDto.class))).thenReturn(category);

        when(categoryBudgetRepository.findCategoryBudgetByUserIdAndCategoryId(anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        var budget = new CategoryBudget();
        budget.setId(1L);
        budget.setCategory(category);
        budget.setUser(user);
        when(categoryBudgetRepository.save(any(CategoryBudget.class))).thenReturn(budget);

        var budgetDto = new CategoryBudgetDto("r.gholamzad", CategoryTypeDto.COFFEE, new BigDecimal(500000));
        var savedBudget = categoryBudgetService.setCategoryBudget(budgetDto);

        Assertions.assertEquals(1, savedBudget.budgetId());
        Assertions.assertEquals(new BigDecimal(500000), savedBudget.budgetDto().budget());
        verify(userService, times(1)).getUserEntityByUsername("r.gholamzad");
        verify(categoryService, times(1)).getCategory(CategoryTypeDto.COFFEE);
        verify(categoryBudgetRepository, times(1)).save(any());
        verify(categoryBudgetRepository, times(1)).findCategoryBudgetByUserIdAndCategoryId(anyLong(), anyLong());
    }

    @Test
    public void testUpdateBudgetSuccess() throws Exception {
        var user = new User();
        user.setId(1L);
        user.setUsername("r.gholamzad");
        when(userService.getUserEntityByUsername(anyString())).thenReturn(user);

        var category = new Category();
        category.setId(1L);
        category.setName(CategoryTypeDto.COFFEE.toString());
        when(categoryService.getCategory(any(CategoryTypeDto.class))).thenReturn(category);

        var budget = new CategoryBudget();
        budget.setId(2L);
        budget.setCategory(category);
        budget.setUser(user);
        budget.setBudget(new BigDecimal(5000));
        when(categoryBudgetRepository.findCategoryBudgetByUserIdAndCategoryId(anyLong(), anyLong()))
                .thenReturn(Optional.of(budget));

        when(categoryBudgetRepository.save(any(CategoryBudget.class))).thenReturn(budget);

        var budgetDto = new CategoryBudgetDto("r.gholamzad", CategoryTypeDto.COFFEE, new BigDecimal(2000));
        var savedBudget = categoryBudgetService.setCategoryBudget(budgetDto);

        Assertions.assertEquals(2, savedBudget.budgetId());
        Assertions.assertEquals(new BigDecimal(2000), savedBudget.budgetDto().budget());
        verify(userService, times(1)).getUserEntityByUsername("r.gholamzad");
        verify(categoryService, times(1)).getCategory(CategoryTypeDto.COFFEE);
        verify(categoryBudgetRepository, times(1)).save(any());
        verify(categoryBudgetRepository, times(1)).findCategoryBudgetByUserIdAndCategoryId(anyLong(), anyLong());
    }
}
