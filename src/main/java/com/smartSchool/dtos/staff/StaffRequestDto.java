package com.smartSchool.dtos.staff;

import com.smartSchool.enums.RoleName;
import lombok.*;
import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffRequestDto {

    @NotBlank(message = "Employee ID is required")
    private String employeeId;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phone;

    private String email;

    @NotNull(message = "Department ID is required")
    private Long departmentId;

    @NotNull(message = "Designation ID is required")
    private Long designationId;

    @PastOrPresent(message = "Joining date cannot be in the future")
    private LocalDate joiningDate;

    private RoleName role;
}
