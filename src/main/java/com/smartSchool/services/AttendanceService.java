package com.smartSchool.services;


import com.smartSchool.dtos.attendance.*;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    AttendanceResponseDto markAttendance(AttendanceRequestDto dto);
    AttendanceResponseDto updateAttendance(Long id, AttendanceRequestDto dto);
    AttendanceResponseDto getAttendanceById(Long id);
    List<AttendanceResponseDto> getAttendanceByDate(LocalDate date);
    List<AttendanceResponseDto> getAttendanceByStudent(Long studentId);
    List<AttendanceResponseDto> getLeaveRequests(String status);
    AttendanceResponseDto approveLeave(Long id, String status);
    void deleteAttendance(Long id);
    List<AttendanceResponseDto> markBulkAttendance(BulkAttendanceRequestDto dto);
}
