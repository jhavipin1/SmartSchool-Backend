package com.smartSchool.dtos.teacher;

import com.smartSchool.dtos.staff.StaffResponseDto;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherResponseDto {
    private Long id;
    private StaffResponseDto staffDetails;
    private List<String> assignedSubjects;
}