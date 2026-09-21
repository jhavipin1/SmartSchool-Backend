package com.smartSchool.dtos.library;

import lombok.*;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor @Builder
public class StudentSearchRequestDto {
    private String name;
    private String admissionNumber;
    private String rollNumber;
}