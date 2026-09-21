package com.smartSchool.services.impl;

import com.smartSchool.dtos.attendance.*;
import com.smartSchool.entities.Attendance;
import com.smartSchool.entities.Student;
import com.smartSchool.repositories.AttendanceRepository;
import com.smartSchool.repositories.StudentRepository;
import com.smartSchool.services.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;

    @Override
    public AttendanceResponseDto markAttendance(AttendanceRequestDto dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Attendance attendance = Attendance.builder()
                .student(student)
                .attendanceDate(dto.getAttendanceDate())
                .status(dto.getStatus())
                .entryTime(dto.getEntryTime())
                .exitTime(dto.getExitTime())
                .note(dto.getNote())
                .source(dto.getSource())
                .leaveStartDate(dto.getLeaveStartDate())
                .leaveEndDate(dto.getLeaveEndDate())
                .leaveStatus(dto.getLeaveStatus())
                .build();

        attendanceRepository.save(attendance);
        return mapToResponse(attendance);
    }

    @Override
    public AttendanceResponseDto updateAttendance(Long id, AttendanceRequestDto dto) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        attendance.setStudent(student);
        attendance.setAttendanceDate(dto.getAttendanceDate());
        attendance.setStatus(dto.getStatus());
        attendance.setEntryTime(dto.getEntryTime());
        attendance.setExitTime(dto.getExitTime());
        attendance.setNote(dto.getNote());
        attendance.setSource(dto.getSource());
        attendance.setLeaveStartDate(dto.getLeaveStartDate());
        attendance.setLeaveEndDate(dto.getLeaveEndDate());
        attendance.setLeaveStatus(dto.getLeaveStatus());

        attendanceRepository.save(attendance);
        return mapToResponse(attendance);
    }

    @Override
    public AttendanceResponseDto getAttendanceById(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));
        return mapToResponse(attendance);
    }

    @Override
    public List<AttendanceResponseDto> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByAttendanceDate(date)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceResponseDto> getAttendanceByStudent(Long studentId) {
        return attendanceRepository.findByStudentId(studentId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceResponseDto> getLeaveRequests(String status) {
        return attendanceRepository.findByLeaveStatus(status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AttendanceResponseDto approveLeave(Long id, String status) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        attendance.setLeaveStatus(status);
        attendanceRepository.save(attendance);
        return mapToResponse(attendance);
    }

    @Override
    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }

    private AttendanceResponseDto mapToResponse(Attendance attendance) {
        return AttendanceResponseDto.builder()
                .id(attendance.getId())
                .studentId(attendance.getStudent().getId())
                .studentName(attendance.getStudent().getFirstName() + " " + attendance.getStudent().getLastName())
                .attendanceDate(attendance.getAttendanceDate())
                .status(attendance.getStatus())
                .entryTime(attendance.getEntryTime())
                .exitTime(attendance.getExitTime())
                .note(attendance.getNote())
                .source(attendance.getSource())
                .leaveStartDate(attendance.getLeaveStartDate())
                .leaveEndDate(attendance.getLeaveEndDate())
                .leaveStatus(attendance.getLeaveStatus())
                .build();
    }

    @Override
    public List<AttendanceResponseDto> markBulkAttendance(BulkAttendanceRequestDto dto) {
        return dto.getAttendanceList().stream()
                .map(this::markAttendance) // reuse single-student method
                .collect(Collectors.toList());
    }

}
