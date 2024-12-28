package com.snapppay.expensetracker.service;

import com.snapppay.expensetracker.entity.Bill;
import com.snapppay.expensetracker.model.CategoryType;
import com.snapppay.expensetracker.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategorizedBillService {

    private final BillRepository billRepository;
    private final UserService userService;

    public Map<CategoryType, BigDecimal> getSummaryCategorizedBills(String username, ZonedDateTime from, ZonedDateTime to) {
        var user = userService.getUserByUsername(username);
        var userBills = billRepository.findAllByUserIdAndCreationDateBetween(user.getId(), from, to);
        return generateSummaryCategorizedBills(userBills);
    }

    private Map<CategoryType, BigDecimal> generateSummaryCategorizedBills(Set<Bill> userBills) {
        var summaryCategorizedBills = new HashMap<CategoryType, BigDecimal>();
        userBills.forEach(userbill -> {
            var categoryTypeDto = CategoryType.valueOf(userbill.getCategory().getName());
            summaryCategorizedBills.computeIfPresent(categoryTypeDto, (key, categoryAmount) -> categoryAmount.add(userbill.getAmount()));
            if (!summaryCategorizedBills.containsKey(categoryTypeDto)) {
                summaryCategorizedBills.put(categoryTypeDto, userbill.getAmount());
            }
        });
        return summaryCategorizedBills;
    }
}
