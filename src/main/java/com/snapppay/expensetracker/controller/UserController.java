package com.snapppay.expensetracker.controller;

import com.snapppay.expensetracker.exception.FailureResponse;
import com.snapppay.expensetracker.model.LoginRequest;
import com.snapppay.expensetracker.model.LoginResponse;
import com.snapppay.expensetracker.security.AuthenticationService;
import com.snapppay.expensetracker.util.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiBasic.BASIC_API + "/user")
@RequiredArgsConstructor
@Tag(name = "User authentication api")
public class UserController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful - login user data"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = FailureResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid user or password",
                            content = @Content(schema = @Schema(implementation = FailureResponse.class)))
            }
    )
    @Operation(summary = "Authentication operation",
            description = "Receive Access token for subsequence requests and user information")
    public LoginResponse login(@ModelAttribute LoginRequest request) {
        var expenseTrackerUserDetail = authenticationService.login(request);
        var accessToken = JWTUtil.createJwt(expenseTrackerUserDetail.getUsername());
        var user = expenseTrackerUserDetail.user();
        return new LoginResponse(accessToken, user.getUsername(), user.getFirstName(), user.getLastName());
    }
}
