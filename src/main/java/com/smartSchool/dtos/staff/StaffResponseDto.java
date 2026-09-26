package com.smartSchool.dtos.staff;

import lombok.*;
import java.time.LocalDate;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffResponseDto {
    private Long id;
    private String employeeId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String role;
    private String departmentName;
    private String designationName;
    private LocalDate joiningDate;
    private String username;
    private boolean active;
}
