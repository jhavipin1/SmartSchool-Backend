package com.smartSchool.services.impl;

import com.smartSchool.dtos.staff.StaffAttendanceDTO;
import com.smartSchool.entities.Staff;
import com.smartSchool.entities.StaffAttendance;
import com.smartSchool.repositories.StaffAttendanceRepository;
import com.smartSchool.repositories.StaffRepository;
import com.smartSchool.services.StaffAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaffAttendanceServiceImpl implements StaffAttendanceService {

    private final StaffAttendanceRepository attendanceRepository;
    private final StaffRepository staffRepository;

    @Override
    public StaffAttendanceDTO markAttendance(StaffAttendanceDTO dto) {
        Staff staff = staffRepository.findById(dto.getStaffId())
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        StaffAttendance attendance = StaffAttendance.builder()
                .attendanceDate(dto.getAttendanceDate())
                .status(dto.getStatus())
                .entryTime(dto.getEntryTime())
                .exitTime(dto.getExitTime())
                .note(dto.getNote())
                .source(dto.getSource())
                .staff(staff)
                .build();
        return mapToDTO(attendanceRepository.save(attendance));
    }

    @Override
    public StaffAttendanceDTO updateAttendance(Long id, StaffAttendanceDTO dto) {
        StaffAttendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));
        attendance.setStatus(dto.getStatus());
        attendance.setEntryTime(dto.getEntryTime());
        attendance.setExitTime(dto.getExitTime());
        attendance.setNote(dto.getNote());
        attendance.setSource(dto.getSource());
        return mapToDTO(attendanceRepository.save(attendance));
    }

    @Override
    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }

    @Override
    public List<StaffAttendanceDTO> getAttendanceByStaff(Long staffId, LocalDate start, LocalDate end) {
        return attendanceRepository.findByStaffIdAndAttendanceDateBetween(staffId, start, end)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<StaffAttendanceDTO> getAllAttendance() {
        return attendanceRepository.findAll()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private StaffAttendanceDTO mapToDTO(StaffAttendance attendance) {
        return StaffAttendanceDTO.builder()
                .id(attendance.getId())
                .attendanceDate(attendance.getAttendanceDate())
                .status(attendance.getStatus())
                .entryTime(attendance.getEntryTime())
                .exitTime(attendance.getExitTime())
                .note(attendance.getNote())
                .source(attendance.getSource())
                .staffId(attendance.getStaff().getId())
                .staffName(attendance.getStaff().getFirstName() + " " + attendance.getStaff().getLastName())
                .build();
    }
}

