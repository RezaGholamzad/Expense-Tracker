package com.snapppay.expensetracker.model;

import io.swagger.v3.oas.annotations.media.Schema;

public record CategoryBudgetRequest(@Schema(requiredMode = Schema.RequiredMode.REQUIRED) CategoryType categoryType,
                                    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String budget) {
}
