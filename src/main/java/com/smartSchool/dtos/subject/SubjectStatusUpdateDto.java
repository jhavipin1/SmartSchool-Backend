package com.smartSchool.dtos.subject;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SubjectStatusUpdateDto {
    @NotNull(message = "Active status is required")
    private Boolean active;
}

