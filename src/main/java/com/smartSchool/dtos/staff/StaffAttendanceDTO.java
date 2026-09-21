package com.smartSchool.dtos.staff;

import com.smartSchool.enums.AttendanceStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffAttendanceDTO {
    private Long id;
    private LocalDate attendanceDate;
    private AttendanceStatus status;
    private LocalTime entryTime;
    private LocalTime exitTime;
    private String note;
    private String source;
    private Long staffId;
    private String staffName; // convenience field
}

