package com.snapppay.expensetracker.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record BillRequest(@Schema(requiredMode = Schema.RequiredMode.REQUIRED) BigDecimal amount,
                          @Schema(requiredMode = Schema.RequiredMode.REQUIRED) CategoryType categoryType,
                          String title) {
}
