package com.smartSchool.dtos.student;

import com.smartSchool.enums.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponseDto {

    private Long id;
    private String admissionNumber;
    private String rollNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private LocalDate dateOfBirth;

    private Gender gender;
    private Category category;
    private Religion religion;
    private BloodGroup bloodGroup;
    private House house;

    // Class & Section Brief Info
    private Long classNameId;
    private String className;
    private Long sectionId;
    private String sectionName;

    // Linked User & Parent IDs
    private Long userId;
    private String userUsername;
    private Long parentId;

    private String mobileNo;
    private String email;
    private LocalDate admissionDate;

    private BigDecimal height;
    private BigDecimal weight;
    private LocalDate measurementDate;

    // Guardian Information
    private String fatherName;
    private String fatherPhone;
    private String fatherOcc;
    private String motherName;
    private String motherPhone;
    private String motherOcc;
    private String guardianIs;
    private String guardianName;
    private String guardianRelation;
    private String guardianEmail;
    private String guardianPhone;
    private String guardianOcc;
    private String guardianAddress;

    // Addresses & Misc Details
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
