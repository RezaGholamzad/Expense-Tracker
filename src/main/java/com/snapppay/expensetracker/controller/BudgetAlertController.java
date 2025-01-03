package com.snapppay.expensetracker.controller;

import com.snapppay.expensetracker.context.ExpenseTrackerContextUtil;
import com.snapppay.expensetracker.exception.FailureResponse;
import com.snapppay.expensetracker.model.BudgetAlertResponse;
import com.snapppay.expensetracker.service.BudgetAlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiBasic.BASIC_API + "/user")
@RequiredArgsConstructor
@Tag(name = "It warns the user about budget compliance")
public class BudgetAlertController {

    private final BudgetAlertService budgetAlertService;
    private final ExpenseTrackerContextUtil expenseTrackerContextUtil;

    @GetMapping(value = "/budget-alert")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = FailureResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid user or password",
                            content = @Content(schema = @Schema(implementation = FailureResponse.class)))
            }
    )
    @Operation(summary = "User budget alert", description = "Receive alert on budget")
    public BudgetAlertResponse getUserBudgetAlert() {
        var budgetAlerts = budgetAlertService.getBudgetAlerts(expenseTrackerContextUtil.getUsername());
        return new BudgetAlertResponse(budgetAlerts);
    }

}
