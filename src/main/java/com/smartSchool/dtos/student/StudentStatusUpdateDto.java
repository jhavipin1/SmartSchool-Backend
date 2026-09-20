package com.smartSchool.dtos.student;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudentStatusUpdateDto {
    @NotNull(message = "Class ID cannot be null")
    private Long classId;

    @NotNull(message = "Section ID cannot be null")
    private Long sectionId;

    private String reason;
}