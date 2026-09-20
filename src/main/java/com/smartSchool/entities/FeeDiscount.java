package com.smartSchool.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "fee_discounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Discount Code is required")
    @Size(max = 50, message = "Discount Code must not exceed 50 characters")
    @Column(name = "discount_code", nullable = false, unique = true, length = 50)
    private String discountCode;

    @NotNull(message = "Discount Type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @DecimalMin(value = "0.0", inclusive = false, message = "Percentage must be greater than 0")
    @DecimalMax(value = "100.0", message = "Percentage cannot exceed 100")
    @Column(name = "percentage", precision = 5, scale = 2)
    private BigDecimal percentage;

    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @NotNull(message = "Number of Use Count is required")
    @Min(value = 1, message = "Number of use count must be at least 1")
    @Column(name = "use_count", nullable = false)
    private Integer numberOfUseCount;

    @Future(message = "Expiry Date must be in the future")
    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Column(name = "description", length = 500)
    private String description;

    public enum DiscountType {
        PERCENTAGE,
        FIX_AMOUNT
    }
}