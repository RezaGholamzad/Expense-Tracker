package com.snapppay.expensetracker.mock;


import com.snapppay.expensetracker.entity.Bill;
import com.snapppay.expensetracker.entity.Category;
import com.snapppay.expensetracker.entity.CategoryBudget;
import com.snapppay.expensetracker.entity.User;
import com.snapppay.expensetracker.repository.BillRepository;
import com.snapppay.expensetracker.repository.CategoryBudgetRepository;
import com.snapppay.expensetracker.repository.CategoryRepository;
import com.snapppay.expensetracker.repository.UserRepository;
import com.snapppay.expensetracker.security.ExpenseTrackerPasswordEncoder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
//@Profile("dev")
public class MockDataGeneration {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final BillRepository billRepository;
    private final CategoryBudgetRepository categoryBudgetRepository;
    private final ExpenseTrackerPasswordEncoder expenseTrackerPasswordEncoder;

    @PostConstruct
    public void init() {
        var u1 = new User();
        u1.setUsername("r.gholamzad");
        u1.setPassword(expenseTrackerPasswordEncoder.encode("123"));
        u1.setFirstName("John");
        u1.setLastName("Doe");
        userRepository.save(u1);

        var category = new Category();
        category.setName("COFFEE");
        categoryRepository.save(category);

        var category1 = new Category();
        category1.setName("CAR");
        categoryRepository.save(category1);

        var category2 = new Category();
        category2.setName("FOOD");
        categoryRepository.save(category2);

        var category3 = new Category();
        category3.setName("GAME");
        categoryRepository.save(category3);

        // COFFEE
        var bill = new Bill();
        bill.setAmount(new BigDecimal("100.00"));
        bill.setCategory(category);
        bill.setUser(u1);
        bill.setCreationDate(ZonedDateTime.now());
        billRepository.save(bill);

        bill = new Bill();
        bill.setAmount(new BigDecimal("400.00"));
        bill.setCategory(category);
        bill.setUser(u1);
        bill.setCreationDate(ZonedDateTime.now());
        billRepository.save(bill);

        // CAR
        bill = new Bill();
        bill.setAmount(new BigDecimal("200.00"));
        bill.setCategory(category1);
        bill.setUser(u1);
        bill.setCreationDate(ZonedDateTime.now());
        billRepository.save(bill);

        // GAME
        bill = new Bill();
        bill.setAmount(new BigDecimal("200.00"));
        bill.setCategory(category3);
        bill.setUser(u1);
        bill.setCreationDate(ZonedDateTime.now());
        billRepository.save(bill);

        var categoryBudget = new CategoryBudget();
        categoryBudget.setCategory(category);
        categoryBudget.setUser(u1);
        categoryBudget.setBudget(new BigDecimal(200));
        categoryBudgetRepository.save(categoryBudget);

        categoryBudget = new CategoryBudget();
        categoryBudget.setCategory(category2);
        categoryBudget.setUser(u1);
        categoryBudget.setBudget(new BigDecimal(50));
        categoryBudgetRepository.save(categoryBudget);

        categoryBudget = new CategoryBudget();
        categoryBudget.setCategory(category3);
        categoryBudget.setUser(u1);
        categoryBudget.setBudget(new BigDecimal(500));
        categoryBudgetRepository.save(categoryBudget);
    }
}
