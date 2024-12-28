package com.snapppay.expensetracker.exception;

public class ExpenseTrackerRuntimeException extends RuntimeException {
    public ExpenseTrackerRuntimeException(String message) {
        super(message);
    }

    public ExpenseTrackerRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
