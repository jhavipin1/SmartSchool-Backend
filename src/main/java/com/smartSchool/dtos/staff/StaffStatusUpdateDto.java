package com.smartSchool.dtos.staff;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffStatusUpdateDto {

    @NotNull(message = "Active status must be provided")
    private Boolean active;
}


