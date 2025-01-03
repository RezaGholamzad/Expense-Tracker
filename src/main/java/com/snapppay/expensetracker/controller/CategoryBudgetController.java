package com.snapppay.expensetracker.controller;

import com.snapppay.expensetracker.context.ExpenseTrackerContextUtil;
import com.snapppay.expensetracker.exception.FailureResponse;
import com.snapppay.expensetracker.model.CategoryBudgetRequest;
import com.snapppay.expensetracker.model.CategoryBudgetResponse;
import com.snapppay.expensetracker.service.CategoryBudgetUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiBasic.BASIC_API + "/user")
@RequiredArgsConstructor
@Tag(name = "Category budget controller")
public class CategoryBudgetController {

    private final CategoryBudgetUpdateService categoryBudgetUpdateService;
    private final ExpenseTrackerContextUtil expenseTrackerContextUtil;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = FailureResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid authorization token",
                            content = @Content(schema = @Schema(implementation = FailureResponse.class)))
            }
    )
    @Operation(summary = "Add category budget operation", description = "Add category budget to alert the user ")
    @PostMapping("/category-budget")
    public CategoryBudgetResponse addCategoryBudget(@RequestBody CategoryBudgetRequest request) {
        return categoryBudgetUpdateService.setCategoryBudget(request, expenseTrackerContextUtil.getUsername());
    }
}
