package com.snapppay.expensetracker.exception;

public class UnAuthorizedException extends ExpenseTrackerRuntimeException {
    public UnAuthorizedException(String message) {
        super(message);
    }

    public UnAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
