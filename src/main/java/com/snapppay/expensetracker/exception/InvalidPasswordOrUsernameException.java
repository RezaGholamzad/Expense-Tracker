package com.snapppay.expensetracker.exception;

public class InvalidPasswordOrUsernameException extends ExpenseTrackerRuntimeException {

    public InvalidPasswordOrUsernameException(String message) {
        super(message);
    }
}
