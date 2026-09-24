package com.smartSchool.dtos.teacher;

import com.smartSchool.dtos.staff.StaffRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherRequestDto {
    @Valid
    @NotNull(message = "Staff details are required")
    private StaffRequestDto staff;

    private List<Long> subjectIds;
}