package com.snapppay.expensetracker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    static final String LOGIN_URL = "api/user/login";

    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager,
                                                      CorsProperties corsProperties) throws Exception {
        return  http
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(corsConfigurer -> corsConfigurer.configurationSource(exchange -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.addAllowedHeader(corsProperties.getAllowedOrigins());
                    config.addAllowedMethod("*");
                    config.addAllowedHeader("*");
                    return config;
                    }))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .requestMatchers(HttpMethod.POST, LOGIN_URL).permitAll()
                                .anyRequest().authenticated()
                )
                .securityContext(securityContextConfigurer -> securityContextConfigurer.requireExplicitSave(false))
                .addFilterBefore(new UserAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(sessionConfigurer ->
                        sessionConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();

    }
}
