package com.smartSchool.dtos.staff;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveTypeDTO {
    private Long id;

    @NotBlank(message = "Leave type name is required")
    private String name;
}
