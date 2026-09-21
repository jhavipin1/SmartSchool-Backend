package com.smartSchool.dtos.attendance;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BulkAttendanceRequestDto {

    @NotEmpty(message = "Attendance list cannot be empty")
    private List<AttendanceRequestDto> attendanceList;
}
