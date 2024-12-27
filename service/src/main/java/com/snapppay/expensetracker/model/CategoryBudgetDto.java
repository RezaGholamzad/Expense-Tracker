package com.snapppay.expensetracker.model;

import java.math.BigDecimal;

public record CategoryBudgetDto(String username, CategoryTypeDto categoryTypeDto, BigDecimal budget) {
}
