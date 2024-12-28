package com.snapppay.expensetracker.model;

import java.math.BigDecimal;

public record BudgetAlertDto(CategoryTypeDto categoryType, BigDecimal budgetAmount, BigDecimal amountSpent) {
}
