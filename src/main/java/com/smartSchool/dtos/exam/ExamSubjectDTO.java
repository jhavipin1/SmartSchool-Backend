package com.smartSchool.dtos.exam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamSubjectDTO {
    private Long id;
    private Long subjectId;
    private Long classId;
    private Integer maxMarks;
}