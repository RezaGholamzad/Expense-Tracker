package com.snapppay.expensetracker.model;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequest(@Schema(requiredMode = Schema.RequiredMode.REQUIRED) String username,
                           @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String password){
}