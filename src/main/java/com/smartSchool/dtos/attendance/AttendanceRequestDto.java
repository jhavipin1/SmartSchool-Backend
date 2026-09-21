package com.smartSchool.dtos.attendance;
import com.smartSchool.enums.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AttendanceRequestDto {
    @NotNull private Long studentId;
    @NotNull private LocalDate attendanceDate;
    @NotNull private AttendanceStatus status;
    private LocalTime entryTime;
    private LocalTime exitTime;
    private String note;
    private String source;
    private LocalDate leaveStartDate;
    private LocalDate leaveEndDate;
    private String leaveStatus;
}
