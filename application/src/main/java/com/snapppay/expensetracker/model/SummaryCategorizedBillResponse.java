package com.snapppay.expensetracker.model;

import java.math.BigDecimal;
import java.util.Map;

public record SummaryCategorizedBillResponse(Map<CategoryType, BigDecimal> summaryBill) {
}
