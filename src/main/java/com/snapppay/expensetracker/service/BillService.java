package com.snapppay.expensetracker.service;

import com.snapppay.expensetracker.entity.Bill;
import com.snapppay.expensetracker.entity.Category;
import com.snapppay.expensetracker.entity.User;
import com.snapppay.expensetracker.model.BillRequest;
import com.snapppay.expensetracker.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public Integer addBill(BillRequest request, String username) {
        var user = userService.getUserByUsername(username);
        var category = categoryService.getCategory(request.categoryType());
        var billEntity = convert(request, user, category);
        return billRepository.save(billEntity).getId();
    }


    private Bill convert(BillRequest request, User user, Category category) {
        Bill bill = new Bill();
        bill.setAmount(request.amount());
        bill.setCategory(category);
        bill.setTitle(request.title());
        bill.setUser(user);
        bill.setCreationDate(ZonedDateTime.now());
        return bill;
    }
}
