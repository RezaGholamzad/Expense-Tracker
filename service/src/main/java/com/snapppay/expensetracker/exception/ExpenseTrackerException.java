package com.snapppay.expensetracker.exception;

public class ExpenseTrackerException extends Exception {
    public ExpenseTrackerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpenseTrackerException(String message) {
        super(message);
    }
}
