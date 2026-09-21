package com.smartSchool.dtos.subject;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SubjectRequestDto {
    @NotBlank(message = "Subject name is required")
    private String name;
    private String code;
    private String description;
}
