package com.snapppay.expensetracker.context;

import org.springframework.security.core.context.SecurityContextHolder;

public class ExpenseTrackerContextUtil {

    public static String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
