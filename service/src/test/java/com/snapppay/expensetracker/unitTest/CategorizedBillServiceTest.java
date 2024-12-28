package com.snapppay.expensetracker.unitTest;

import com.snapppay.expensetracker.entity.Bill;
import com.snapppay.expensetracker.entity.Category;
import com.snapppay.expensetracker.entity.User;
import com.snapppay.expensetracker.model.CategoryTypeDto;
import com.snapppay.expensetracker.repository.BillRepository;
import com.snapppay.expensetracker.service.CategorizedBillService;
import com.snapppay.expensetracker.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategorizedBillServiceTest {

    @InjectMocks
    private CategorizedBillService categorizedBillService;

    @Mock
    private BillRepository billRepository;

    @Mock
    private UserService userService;

    @Test
    public void testSummaryCategorizedBillsWhenUserHasCategorizedBill() {
        setUp();
        var from = ZonedDateTime.now().minusDays(30);
        var to = ZonedDateTime.now();
        var categorizedBills = categorizedBillService.getSummaryCategorizedBills("r.gholamzad", from, to);

        Assertions.assertEquals(3, categorizedBills.size());
        Assertions.assertEquals(new BigDecimal("500.00"), categorizedBills.get(CategoryTypeDto.COFFEE));
        Assertions.assertEquals(new BigDecimal("200.00"), categorizedBills.get(CategoryTypeDto.CAR));

        verify(billRepository, times(1)).findAllByUserIdAndCreationDateBetween(1L, from, to);
        verify(userService, times(1)).getUserEntityByUsername("r.gholamzad");
    }

    @Test
    public void testSummaryCategorizedBillsWhenUserDoesNotHaveCategorizedBill() {
        var user = new User();
        user.setId(1L);
        user.setUsername("r.gholamzad");
        when(userService.getUserEntityByUsername(anyString())).thenReturn(user);

        when(billRepository.findAllByUserIdAndCreationDateBetween(anyLong(), any(), any())).thenReturn(Set.of());

        var from = ZonedDateTime.now().minusDays(30);
        var to = ZonedDateTime.now();
        var categorizedBills = categorizedBillService.getSummaryCategorizedBills("r.gholamzad", from, to);

        Assertions.assertTrue(categorizedBills.isEmpty());

        verify(billRepository, times(1)).findAllByUserIdAndCreationDateBetween(1L, from, to);
        verify(userService, times(1)).getUserEntityByUsername("r.gholamzad");
    }

    private void setUp() {
        var user = new User();
        user.setId(1L);
        user.setUsername("r.gholamzad");
        when(userService.getUserEntityByUsername(anyString())).thenReturn(user);

        var userBill = new HashSet<Bill>();

        var category = new Category();
        category.setName("COFFEE");

        var category2 = new Category();
        category2.setName("CAR");

        var category3 = new Category();
        category3.setName("FOOD");

        // COFFEE
        var bill = new Bill();
        bill.setId(1);
        bill.setAmount(new BigDecimal("100.00"));
        bill.setCategory(category);
        bill.setUser(user);
        bill.setCreationDate(ZonedDateTime.now());
        userBill.add(bill);

        var bill2 = new Bill();
        bill2.setId(2);
        bill2.setAmount(new BigDecimal("400.00"));
        bill2.setCategory(category);
        bill2.setUser(user);
        bill2.setCreationDate(ZonedDateTime.now());
        userBill.add(bill2);

        // CAR
        var bill3 = new Bill();
        bill3.setId(3);
        bill3.setAmount(new BigDecimal("200.00"));
        bill3.setCategory(category2);
        bill3.setUser(user);
        bill3.setCreationDate(ZonedDateTime.now());
        userBill.add(bill3);

        // FOOD
        var bill4 = new Bill();
        bill4.setId(4);
        bill4.setAmount(new BigDecimal("200.00"));
        bill4.setCategory(category3);
        bill4.setUser(user);
        bill4.setCreationDate(ZonedDateTime.now());
        userBill.add(bill4);

        when(billRepository.findAllByUserIdAndCreationDateBetween(anyLong(), any(), any())).thenReturn(userBill);
    }
}
