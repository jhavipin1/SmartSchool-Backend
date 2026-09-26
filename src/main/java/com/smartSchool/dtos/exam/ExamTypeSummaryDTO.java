package com.smartSchool.dtos.exam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamTypeSummaryDTO {
    private Long examTypeId;
    private String examTypeName;
    private Long classId;
    private Long subjectId;
    private Double averageMarks;
    private Integer highestMarks;
    private Integer lowestMarks;
}
