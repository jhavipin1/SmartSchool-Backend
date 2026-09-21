package com.smartSchool.dtos.homework;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HomeworkRequestDto {
    @NotNull private Long classId;
    @NotNull private Long sectionId;
    @NotNull private Long subjectId;
    @NotNull private LocalDate homeworkDate;
    @NotNull private LocalDate submissionDate;
    private LocalDate evaluationDate;
    private Integer maxMarks;
    private String description;
    private String documentPath;
    private String createdBy;
}
