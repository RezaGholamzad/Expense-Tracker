package com.snapppay.expensetracker.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.security.cors")
public class CorsProperties {
    private String allowedOrigins;
}
