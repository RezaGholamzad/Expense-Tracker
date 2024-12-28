package com.snapppay.expensetracker.context;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ExpenseTrackerContextUtil {

    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
