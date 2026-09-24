package com.smartSchool.dtos.student;

import com.smartSchool.enums.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentRequestDto {

    @NotBlank(message = "Admission number is required")
    private String admissionNumber;

    private String rollNumber;

    @NotBlank(message = "First name is required")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    // Enums
    private Gender gender;
    private Category category;
    private Religion religion;
    private BloodGroup bloodGroup;
    private House house;

    // Foreign Key IDs
    private Long classNameId;
    private Long sectionId;
    private Long parentId;

    private String username;
    private String password;

    // Contact Details
    private String mobileNo;

    @Email(message = "Invalid email format")
    private String email;

    private LocalDate admissionDate;

    @DecimalMin(value = "0.0", message = "Height must be positive")
    private BigDecimal height;

    @DecimalMin(value = "0.0", message = "Weight must be positive")
    private BigDecimal weight;

    private LocalDate measurementDate;

    // Guardian Details
    private String fatherName;
    private String fatherPhone;
    private String fatherOcc;

    private String motherName;
    private String motherPhone;
    private String motherOcc;

    private String guardianIs;
    private String guardianName;
    private String guardianRelation;

    @Email(message = "Invalid guardian email format")
    private String guardianEmail;
    private String guardianPhone;
    private String guardianOcc;
    private String guardianAddress;

    // Addresses & Financial Info
    private String currentAddress;
    private String permanentAddress;

    private String bankAccountNo;
    private String bankName;
    private String ifscCode;

    private String nationalIdentificationNo;
    private String localIdentificationNo;
    private String rte;
    private String previousSchool;
    private String note;
}