package com.snapppay.expensetracker.service;

import com.snapppay.expensetracker.model.BudgetAlert;
import com.snapppay.expensetracker.model.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BudgetAlertService {

    private final CategoryBudgetService categoryBudgetService;
    private final CategorizedBillService categorizedBillService;

    public Set<BudgetAlert> getBudgetAlerts(String username) {
        var summaryCategorizedBills = getCurrentMonthCategorizedBill(username);
        var categorizedBudget = categoryBudgetService.getCategoryBudget(username);
        return getBudgetAlertOnCategorizedBill(summaryCategorizedBills, categorizedBudget);
    }

    private Map<CategoryType, BigDecimal> getCurrentMonthCategorizedBill(String username) {
        var now = ZonedDateTime.now();
        var firstOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        var lengthOfMonth = now.getMonth().length(now.toLocalDate().isLeapYear());
        var lastOfMonth = now.withDayOfMonth(lengthOfMonth).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        return categorizedBillService.getSummaryCategorizedBills(username, firstOfMonth, lastOfMonth);
    }

    private Set<BudgetAlert> getBudgetAlertOnCategorizedBill(Map<CategoryType, BigDecimal> summaryCategorizedBills,
                                                             Map<CategoryType, BigDecimal> categorizedBudget) {
        Set<BudgetAlert> budgetAlertDtos = new HashSet<>();
        summaryCategorizedBills
                .forEach((category, amountSpent) -> {
                    if (categorizedBudget.containsKey(category)) {
                        var budget = categorizedBudget.get(category);
                        if (amountSpent.compareTo(budget) > 0) {
                            budgetAlertDtos.add(new BudgetAlert(category, budget, amountSpent));
                        }
                    }
                });
        return budgetAlertDtos;
    }
}
