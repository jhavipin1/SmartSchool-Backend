package com.smartSchool.dtos.fee;

import com.smartSchool.enums.FeePaymentStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeRequestDto {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    private Long feeGroupId;

    @NotNull(message = "Fee Type ID is required")
    private Long feeTypeId;

    private Long feeDiscountId;

    @NotNull(message = "Due Date is required")
    private LocalDate dueDate;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", message = "Amount must be zero or positive")
    private BigDecimal amount;

    @DecimalMin(value = "0.0", message = "Paid Amount must be zero or positive")
    private BigDecimal paidAmount;

    @DecimalMin(value = "0.0", message = "Fine Amount must be zero or positive")
    private BigDecimal fineAmount;

    @DecimalMin(value = "0.0", message = "Discount Amount must be zero or positive")
    private BigDecimal discountAmount;

    private FeePaymentStatus status;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
}
