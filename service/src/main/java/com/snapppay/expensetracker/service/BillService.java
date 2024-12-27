package com.snapppay.expensetracker.service;

import com.snapppay.expensetracker.entity.Bill;
import com.snapppay.expensetracker.entity.Category;
import com.snapppay.expensetracker.entity.User;
import com.snapppay.expensetracker.model.BillDto;
import com.snapppay.expensetracker.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public Integer addBill(BillDto bill) {
        var user = userService.getUserEntityByUsername(bill.username());
        var category = categoryService.getCategory(bill.categoryType());
        var billEntity = convert(bill, user, category);
        return billRepository.save(billEntity).getId();
    }


    private Bill convert(BillDto billDto, User user, Category category) {
        Bill bill = new Bill();
        bill.setAmount(billDto.amount());
        bill.setCategory(category);
        bill.setTitle(billDto.title());
        bill.setUser(user);
        bill.setCreationDate(new Date());
        return bill;
    }
}
