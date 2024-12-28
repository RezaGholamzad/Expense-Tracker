package com.snapppay.expensetracker.security;

import com.snapppay.expensetracker.exception.InvalidPasswordOrUsernameException;
import com.snapppay.expensetracker.model.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    public ExpenseTrackerUserDetail login(LoginRequest loginRequestDto) {
        if (loginRequestDto == null || loginRequestDto.username() == null || loginRequestDto.password() == null) {
            throw new InvalidPasswordOrUsernameException("Invalid username or password");
        }

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.username(), loginRequestDto.password()));

        return (ExpenseTrackerUserDetail) authentication.getPrincipal();

    }
}
