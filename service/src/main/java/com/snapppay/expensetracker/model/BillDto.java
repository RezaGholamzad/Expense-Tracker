package com.snapppay.expensetracker.model;

import java.math.BigDecimal;

public record BillDto(String username, BigDecimal amount, CategoryTypeDto categoryType, String title) {
}
