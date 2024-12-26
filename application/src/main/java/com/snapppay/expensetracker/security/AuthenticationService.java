package com.snapppay.expensetracker.security;

import com.snapppay.expensetracker.model.LoginRequest;
import com.snapppay.expensetracker.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    public UserDto login(LoginRequest loginRequest) {
        // todo using spring security for login process
        return null;
    }
}
