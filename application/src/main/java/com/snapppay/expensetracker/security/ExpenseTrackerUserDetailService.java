package com.snapppay.expensetracker.security;

import com.snapppay.expensetracker.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExpenseTrackerUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public ExpenseTrackerUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.isEmpty()) {
            log.error("username is null or empty");
            throw new UsernameNotFoundException("Username is empty");
        }

        return new ExpenseTrackerUserDetail(userService.getUserByUsername(username));
    }
}
