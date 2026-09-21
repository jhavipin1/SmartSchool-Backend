package com.smartSchool.dtos.attendance;

import com.smartSchool.enums.AttendanceStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AttendanceResponseDto {
    private Long id;
    private Long studentId;
    private String studentName;
    private LocalDate attendanceDate;
    private AttendanceStatus status;
    private LocalTime entryTime;
    private LocalTime exitTime;
    private String note;
    private String source;
    private LocalDate leaveStartDate;
    private LocalDate leaveEndDate;
    private String leaveStatus;
}
