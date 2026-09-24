package com.smartSchool.dtos.homework;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherHomeworkRequestDto {
    @NotNull(message = "Teacher ID is required")
    private Long teacherId;

    @NotNull(message = "Class ID is required")
    private Long classId;

    @NotNull(message = "Section ID is required")
    private Long sectionId;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    @NotNull(message = "Homework date is required")
    private LocalDate homeworkDate;

    @NotNull(message = "Submission date is required")
    private LocalDate submissionDate;

    private LocalDate evaluationDate;
    private Integer maxMarks;
    private String description;
    private String documentPath;
}