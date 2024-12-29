package com.snapppay.expensetracker.security;

import com.snapppay.expensetracker.exception.InvalidPasswordOrUsernameException;
import com.snapppay.expensetracker.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAuthenticationManager implements AuthenticationManager {

    private final UserDetailsService userDetailsService;
    private final ExpenseTrackerPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication == null) {
            log.error("Authentication is null");
            throw new BadCredentialsException("Invalid username or password");
        }

        if (authentication instanceof JWTAuthenticationToken) {
            var token = authentication.getCredentials().toString();
            var username = validateTokenAndGetUsername(token);
            return new UsernamePasswordAuthenticationToken(username, token, List.of());
        } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
            var userDetails = userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
            if (passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())) {
                return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            }
        }

        log.error("Authentication failed");
        throw new InvalidPasswordOrUsernameException("Invalid username or password");
    }

    private String validateTokenAndGetUsername(String token) {
        return JWTUtil.decodeJwt(token);
    }
}
