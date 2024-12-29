package com.snapppay.expensetracker.model;

public record LoginResponse(String token, String username, String firstName, String lastName) {
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": username: " +
                username + "\n" + "firstName: " +
                firstName + "\n" + "lastName: "
                + lastName + "\n" +
                "token: " + "*********";
    }
}
