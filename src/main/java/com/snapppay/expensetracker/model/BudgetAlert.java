package com.snapppay.expensetracker.model;

import java.math.BigDecimal;

public record BudgetAlert(CategoryType categoryType, BigDecimal budgetAmount, BigDecimal amountSpent) {
}
