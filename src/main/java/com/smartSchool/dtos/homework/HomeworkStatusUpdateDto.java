package com.smartSchool.dtos.homework;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HomeworkStatusUpdateDto {
    @NotNull(message = "Active status is required")
    private Boolean active;
}
