package com.snapppay.expensetracker.model;

import java.math.BigDecimal;

public record CategoryBudgetResponse(Long id, BigDecimal budget, CategoryType category) {
}
