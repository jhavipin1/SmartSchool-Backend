package com.smartSchool.dtos.exam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamSetupRequestDto {
    private String examGroupName;
    private String examTypeName;
    private String subjectName;
    private Integer maxMarks;
    private String className;
}

