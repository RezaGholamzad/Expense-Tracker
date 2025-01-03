package com.snapppay.expensetracker.security;

import com.snapppay.expensetracker.exception.UnAuthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (!isLoginRequest(request)) {
                var accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
                if (accessToken != null && accessToken.startsWith("Bearer ")) {
                    var credentials = accessToken.substring("Bearer ".length());
                    var authenticationToken = authenticationManager.authenticate(new JWTAuthenticationToken(credentials));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } else {
                SecurityContextHolder.clearContext();
            }
        } catch (Exception e) {
            log.error("Error occurred while authorizing request");
            throw new UnAuthorizedException("Error occurred while authorizing request", e);
        }
        filterChain.doFilter(request, response);
    }

    private boolean isLoginRequest(HttpServletRequest request) {
        return request.getRequestURI().endsWith(SecurityConfig.LOGIN_URL);
    }
}
