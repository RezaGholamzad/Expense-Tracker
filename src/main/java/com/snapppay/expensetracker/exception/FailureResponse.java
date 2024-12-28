package com.snapppay.expensetracker.exception;

import io.swagger.v3.oas.annotations.media.Schema;

public record FailureResponse(@Schema(description = "exception name") String exceptionName,
                              @Schema(description = "exception message") String message) {
}
