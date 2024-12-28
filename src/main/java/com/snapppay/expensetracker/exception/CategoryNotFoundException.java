package com.snapppay.expensetracker.exception;

public class CategoryNotFoundException extends ExpenseTrackerRuntimeException{
    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}