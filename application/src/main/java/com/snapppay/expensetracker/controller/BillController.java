package com.snapppay.expensetracker.controller;

import com.snapppay.expensetracker.context.ExpenseTrackerContextUtil;
import com.snapppay.expensetracker.exception.UnAuthorizedException;
import com.snapppay.expensetracker.model.BillDto;
import com.snapppay.expensetracker.model.BillRequest;
import com.snapppay.expensetracker.model.BillResponse;
import com.snapppay.expensetracker.model.CategoryTypeDto;
import com.snapppay.expensetracker.service.BillService;
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
@Tag(name = "User bills controller")
public class BillController {

    private final BillService billService;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful"),
                    @ApiResponse(responseCode = "403", description = "Invalid authorization token",
                            content = @Content(schema = @Schema(implementation = UnAuthorizedException.class)))
            }
    )
    @Operation(summary = "Add categorized bill operation", description = "Add categorized bill operation and get bill id")
    @PostMapping("/bill")
    public BillResponse addBill(@RequestBody BillRequest request) {
        return new BillResponse(billService.addBill(convert(request)));
    }

    private BillDto convert(BillRequest billRequest) {
        return new BillDto(ExpenseTrackerContextUtil.getUsername(), billRequest.amount(),
                CategoryTypeDto.valueOf(billRequest.categoryType().toString()),
                billRequest.title());
    }
}
