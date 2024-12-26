package com.snapppay.expensetracker.exception;

public class UserNotFoundException extends ExpenseTrackerRuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
