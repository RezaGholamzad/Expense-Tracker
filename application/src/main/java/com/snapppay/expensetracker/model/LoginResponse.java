package com.snapppay.expensetracker.model;

public record LoginResponse(String token, String username, String firstName, String lastName) {
}
