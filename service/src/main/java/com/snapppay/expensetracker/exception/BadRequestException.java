package com.snapppay.expensetracker.exception;

public class BadRequestException extends ExpenseTrackerRuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
