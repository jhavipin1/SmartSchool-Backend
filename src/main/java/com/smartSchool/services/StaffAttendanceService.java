package com.smartSchool.services;


import com.smartSchool.dtos.staff.StaffAttendanceDTO;

import java.time.LocalDate;
import java.util.List;

public interface StaffAttendanceService {
    StaffAttendanceDTO markAttendance(StaffAttendanceDTO dto);
    StaffAttendanceDTO updateAttendance(Long id, StaffAttendanceDTO dto);
    void deleteAttendance(Long id);
    List<StaffAttendanceDTO> getAttendanceByStaff(Long staffId, LocalDate start, LocalDate end);
    List<StaffAttendanceDTO> getAllAttendance();
}
