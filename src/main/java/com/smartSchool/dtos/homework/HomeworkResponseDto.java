package com.smartSchool.dtos.homework;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HomeworkResponseDto {
    private Long id;
    private String className;
    private String sectionName;
    private String subjectName;
    private LocalDate homeworkDate;
    private LocalDate submissionDate;
    private LocalDate evaluationDate;
    private Integer maxMarks;
    private String description;
    private String documentPath;
    private String createdBy;
    private Boolean active;
}

