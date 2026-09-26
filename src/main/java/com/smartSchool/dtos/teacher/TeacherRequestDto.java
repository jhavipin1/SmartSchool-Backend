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

    @NotNull(message = "Staff ID is required")
    private Long staffId;

    private List<Long> subjectIds;
}
