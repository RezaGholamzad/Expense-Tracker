package com.snapppay.expensetracker.model;

import io.swagger.v3.oas.annotations.media.Schema;

public record SummaryCategorizedBillRequest(@Schema(requiredMode = Schema.RequiredMode.REQUIRED) String year,
                                            @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String month) {
}
