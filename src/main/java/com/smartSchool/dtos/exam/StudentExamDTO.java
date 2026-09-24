package com.smartSchool.dtos.exam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentExamDTO {
    private Long id;
    private Long studentId;
    private Long examSubjectId;
    private Long sectionId;
    private Integer marksObtained;
}
