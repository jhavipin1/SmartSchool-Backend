package com.smartSchool.entities;

import com.smartSchool.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Admission number is required")
    @Column(name = "admission_number", nullable = false, unique = true, length = 50)
    private String admissionNumber;

    @Column(name = "roll_number", length = 30)
    private String rollNumber;

    @Column(name = "library_card_no", length = 50, unique = true)
    private String libraryCardNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "library_card_status", length = 20)
    private LibraryCardStatus libraryCardStatus; // ACTIVE / INACTIVE

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<IssueRecord> issueRecords = new ArrayList<>();

    @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @NotBlank(message = "Last name is required")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @NotNull(message = "Date of birth is required")
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    // --- Enum Mappings ---

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 20)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", length = 20)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "religion", length = 20)
    private Religion religion;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group", length = 20)
    private BloodGroup bloodGroup;

    @Enumerated(EnumType.STRING)
    @Column(name = "house", length = 20)
    private House house;

    // --- Dynamic Table References ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private ClassName className;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private Section section;

    // --- Contact & Additional Info ---

    @Column(name = "mobile_no", length = 15)
    private String mobileNo;

    @Email
    @Column(length = 100)
    private String email;

    @Column(name = "admission_date")
    private LocalDate admissionDate;

    @DecimalMin(value = "0.0")
    @Column(precision = 5, scale = 2)
    private BigDecimal height;

    @DecimalMin(value = "0.0")
    @Column(precision = 5, scale = 2)
    private BigDecimal weight;

    @Column(name = "measurement_date")
    private LocalDate measurementDate;

    // --- Guardian Details ---

    @Column(name = "father_name", length = 100)
    private String fatherName;

    @Column(name = "father_phone", length = 15)
    private String fatherPhone;

    @Column(name = "father_occ", length = 100)
    private String fatherOcc;

    @Column(name = "mother_name", length = 100)
    private String motherName;

    @Column(name = "mother_phone", length = 15)
    private String motherPhone;

    @Column(name = "mother_occ", length = 100)
    private String motherOcc;

    @Column(name = "guardian_is", length = 50)
    private String guardianIs;

    @Column(name = "guardian_name", length = 100)
    private String guardianName;

    @Column(name = "guardian_relation", length = 50)
    private String guardianRelation;

    @Email
    @Column(name = "guardian_email", length = 100)
    private String guardianEmail;

    @Column(name = "guardian_phone", length = 15)
    private String guardianPhone;

    @Column(name = "guardian_occ", length = 100)
    private String guardianOcc;

    @Column(name = "guardian_address", columnDefinition = "TEXT")
    private String guardianAddress;

    @Column(name = "current_address", columnDefinition = "TEXT")
    private String currentAddress;

    @Column(name = "permanent_address", columnDefinition = "TEXT")
    private String permanentAddress;

    @Column(name = "bank_account_no", length = 30)
    private String bankAccountNo;

    @Column(name = "bank_name", length = 100)
    private String bankName;

    @Column(name = "ifsc_code", length = 20)
    private String ifscCode;

    @Column(name = "national_identification_no", length = 50)
    private String nationalIdentificationNo;

    @Column(name = "local_identification_no", length = 50)
    private String localIdentificationNo;

    @Column(length = 10)
    private String rte;

    @Column(name = "previous_school", length = 150)
    private String previousSchool;

    @Column(columnDefinition = "TEXT")
    private String note;

    // --- Relationships ---

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Parent parent;

    // --- Fee Relationship ---

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<Fee> fees = new ArrayList<>();

    // --- Exam Relationship ---
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<StudentExam> studentExams = new ArrayList<>();
}