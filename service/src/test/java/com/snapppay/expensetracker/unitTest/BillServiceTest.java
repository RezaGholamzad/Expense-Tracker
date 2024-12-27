package com.snapppay.expensetracker.unitTest;

import com.snapppay.expensetracker.entity.Bill;
import com.snapppay.expensetracker.entity.Category;
import com.snapppay.expensetracker.entity.User;
import com.snapppay.expensetracker.exception.CategoryNotFoundException;
import com.snapppay.expensetracker.exception.UserNotFoundException;
import com.snapppay.expensetracker.model.BillDto;
import com.snapppay.expensetracker.model.CategoryTypeDto;
import com.snapppay.expensetracker.repository.BillRepository;
import com.snapppay.expensetracker.service.BillService;
import com.snapppay.expensetracker.service.CategoryService;
import com.snapppay.expensetracker.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BillServiceTest {

    @InjectMocks
    private BillService billService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private UserService userService;
    @Mock
    private BillRepository billRepository;

    @Test
    public void testAddBillSuccess() throws Exception {
        var user = new User();
        user.setId(1L);
        user.setUsername("r.gholamzad");
        when(userService.getUserEntityByUsername(anyString())).thenReturn(user);

        var category = new Category();
        category.setId(1L);
        category.setName(CategoryTypeDto.CAR.toString());
        when(categoryService.getCategory(any(CategoryTypeDto.class))).thenReturn(category);

        var bill = new Bill();
        bill.setId(1);
        bill.setAmount(new BigDecimal(250));
        bill.setCategory(category);
        when(billRepository.save(any(Bill.class))).thenReturn(bill);

        var billDto = new BillDto("r.gholamzad", new BigDecimal(250), CategoryTypeDto.CAR, "fix car");
        var result = billService.addBill(billDto);

        Assertions.assertEquals(1, result);
        verify(userService, times(1)).getUserEntityByUsername("r.gholamzad");
        verify(categoryService, times(1)).getCategory(CategoryTypeDto.CAR);
        verify(billRepository, times(1)).save(any());
    }

    @Test
    public void testAddBillWhenUserNotFound() throws Exception {
        when(userService.getUserEntityByUsername(anyString())).thenThrow(new UserNotFoundException("user not found"));

        var billDto = new BillDto("r.gholamzad", new BigDecimal(250), CategoryTypeDto.CAR, "fix car");

        Assertions.assertThrows(UserNotFoundException.class, () -> billService.addBill(billDto));
        verify(userService, times(1)).getUserEntityByUsername("r.gholamzad");
        verify(categoryService, times(0)).getCategory(any());
        verify(billRepository, times(0)).save(any());
    }

    @Test
    public void testAddBillWhenCategoryNotFound() throws Exception {
        var user = new User();
        user.setId(1L);
        user.setUsername("r.gholamzad");
        when(userService.getUserEntityByUsername(anyString())).thenReturn(user);

        when(categoryService.getCategory(any(CategoryTypeDto.class))).thenThrow(new CategoryNotFoundException("Category not found"));

        var billDto = new BillDto("r.gholamzad", new BigDecimal(250), CategoryTypeDto.CAR, "fix car");
        Assertions.assertThrows(CategoryNotFoundException.class, () -> billService.addBill(billDto));
        verify(userService, times(1)).getUserEntityByUsername("r.gholamzad");
        verify(categoryService, times(1)).getCategory(CategoryTypeDto.CAR);
        verify(billRepository, times(0)).save(any());
    }
}
