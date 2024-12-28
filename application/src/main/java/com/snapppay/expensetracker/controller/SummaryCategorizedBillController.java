package com.snapppay.expensetracker.controller;

import com.snapppay.expensetracker.context.ExpenseTrackerContextUtil;
import com.snapppay.expensetracker.exception.BadRequestException;
import com.snapppay.expensetracker.exception.FailureResponse;
import com.snapppay.expensetracker.model.CategoryType;
import com.snapppay.expensetracker.model.CategoryTypeDto;
import com.snapppay.expensetracker.model.SummaryCategorizedBillRequest;
import com.snapppay.expensetracker.model.SummaryCategorizedBillResponse;
import com.snapppay.expensetracker.service.CategorizedBillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(ApiBasic.BASIC_API + "/user")
@RequiredArgsConstructor
@Tag(name = "Monthly bill summary by category")
public class SummaryCategorizedBillController {

    private final CategorizedBillService categorizedBillService;

    @GetMapping(value = "/monthly-bill-summary")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful"),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = FailureResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid user or password",
                            content = @Content(schema = @Schema(implementation = FailureResponse.class)))
            }
    )
    @Operation(summary = "Monthly bill summary", description = "Receive monthly bill summary by category")
    public SummaryCategorizedBillResponse getUserBudgetAlert(SummaryCategorizedBillRequest request) {
        var from = getFrom(request);
        var lengthOfMonth = from.getMonth().length(from.toLocalDate().isLeapYear());
        var to = ZonedDateTime.of(from.getYear(), from.getMonthValue(), lengthOfMonth,
                23, 59, 59, 999999999, ZoneId.systemDefault());

        var summaryCategorizedBill = categorizedBillService
                .getSummaryCategorizedBills(ExpenseTrackerContextUtil.getUsername(), from, to);
        return convert(summaryCategorizedBill);
    }

    private SummaryCategorizedBillResponse convert(Map<CategoryTypeDto, BigDecimal> categoryTypeDtoMap) {
        var summaryBills = categoryTypeDtoMap.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> CategoryType.valueOf(entry.getKey().toString()), Map.Entry::getValue));
        return new SummaryCategorizedBillResponse(summaryBills);
    }

    private ZonedDateTime getFrom(SummaryCategorizedBillRequest request) {
        int year;
        int month;
        try {
            year = Integer.parseInt(request.year());
            month = Integer.parseInt(request.month());
        } catch (NumberFormatException e) {
            log.error("Invalid year or month", e);
            throw new BadRequestException("The format of year or month is wrong", e);
        }

        return ZonedDateTime.of(year, month, 1,
                0, 0, 0, 0, ZoneId.systemDefault());
    }
}
