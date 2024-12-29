package com.snapppay.expensetracker.unitTest;

import com.snapppay.expensetracker.entity.Bill;
import com.snapppay.expensetracker.entity.Category;
import com.snapppay.expensetracker.entity.User;
import com.snapppay.expensetracker.exception.CategoryNotFoundException;
import com.snapppay.expensetracker.exception.UserNotFoundException;
import com.snapppay.expensetracker.model.BillRequest;
import com.snapppay.expensetracker.model.CategoryType;
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
        when(userService.getUserByUsername(anyString())).thenReturn(user);

        var category = new Category();
        category.setId(1L);
        category.setName(CategoryType.CAR.toString());
        when(categoryService.getCategory(any(CategoryType.class))).thenReturn(category);

        var bill = new Bill();
        bill.setId(1);
        bill.setAmount(new BigDecimal(250));
        bill.setCategory(category);
        bill.setUser(user);
        when(billRepository.save(any(Bill.class))).thenReturn(bill);

        var billRequest = new BillRequest(new BigDecimal(250), CategoryType.CAR, "fix car");
        var result = billService.addBill(billRequest, "r.gholamzad");

        Assertions.assertEquals(1, result);
        verify(userService, times(1)).getUserByUsername("r.gholamzad");
        verify(categoryService, times(1)).getCategory(CategoryType.CAR);
        verify(billRepository, times(1)).save(any());
    }

    @Test
    public void testAddBillWhenUserNotFound() throws Exception {
        when(userService.getUserByUsername(anyString())).thenThrow(new UserNotFoundException("user not found"));

        var request = new BillRequest(new BigDecimal(250), CategoryType.CAR, "fix car");

        Assertions.assertThrows(UserNotFoundException.class, () -> billService.addBill(request, "r.gholamzad"));
        verify(userService, times(1)).getUserByUsername("r.gholamzad");
        verify(categoryService, times(0)).getCategory(any());
        verify(billRepository, times(0)).save(any());
    }

    @Test
    public void testAddBillWhenCategoryNotFound() throws Exception {
        var user = new User();
        user.setId(1L);
        user.setUsername("r.gholamzad");
        when(userService.getUserByUsername(anyString())).thenReturn(user);

        when(categoryService.getCategory(any(CategoryType.class))).thenThrow(new CategoryNotFoundException("Category not found"));

        var request = new BillRequest(new BigDecimal(250), CategoryType.CAR, "fix car");
        Assertions.assertThrows(CategoryNotFoundException.class, () -> billService.addBill(request, "r.gholamzad"));
        verify(userService, times(1)).getUserByUsername("r.gholamzad");
        verify(categoryService, times(1)).getCategory(CategoryType.CAR);
        verify(billRepository, times(0)).save(any());
    }
}
