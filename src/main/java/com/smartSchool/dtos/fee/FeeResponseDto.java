package com.smartSchool.dtos.fee;

import com.smartSchool.enums.FeePaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeResponseDto {

    private Long id;

    // Student basic info
    private Long studentId;
    private String studentName;
    private String admissionNumber;

    // Fee Group info
    private Long feeGroupId;
    private String feeGroupName;

    // Fee Type info
    private Long feeTypeId;
    private String feeTypeName;
    private String feeTypeCode;

    // Fee Discount info
    private Long feeDiscountId;
    private String discountName;
    private String discountCode;

    // Financial details
    private LocalDate dueDate;
    private BigDecimal amount;
    private BigDecimal paidAmount;
    private BigDecimal fineAmount;
    private BigDecimal discountAmount;
    private BigDecimal netPayableAmount;
    private FeePaymentStatus status;
    private String description;
}
