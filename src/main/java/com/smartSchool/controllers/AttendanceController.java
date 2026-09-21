package com.smartSchool.controllers;

import com.smartSchool.dtos.attendance.*;
import com.smartSchool.services.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {


        private final AttendanceService attendanceService;

        // --- Mark Attendance ---
        @PostMapping
        public ResponseEntity<AttendanceResponseDto> markAttendance(@Valid @RequestBody AttendanceRequestDto dto) {
        return ResponseEntity.ok(attendanceService.markAttendance(dto));
    }

        // --- Update Attendance ---
        @PutMapping("/{id}")
        public ResponseEntity<AttendanceResponseDto> updateAttendance(@PathVariable Long id,
            @Valid @RequestBody AttendanceRequestDto dto) {
        return ResponseEntity.ok(attendanceService.updateAttendance(id, dto));
    }

        // --- Get Attendance by ID ---
        @GetMapping("/{id}")
        public ResponseEntity<AttendanceResponseDto> getAttendance(@PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.getAttendanceById(id));
    }

        // --- Get Attendance by Date ---
        @GetMapping("/date/{date}")
        public ResponseEntity<List<AttendanceResponseDto>> getAttendanceByDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok(attendanceService.getAttendanceByDate(date));
    }

        // --- Get Attendance by Student ---
        @GetMapping("/student/{studentId}")
        public ResponseEntity<List<AttendanceResponseDto>> getAttendanceByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByStudent(studentId));
    }

        // --- Get Leave Requests by Status ---
        @GetMapping("/leave/{status}")
        public ResponseEntity<List<AttendanceResponseDto>> getLeaveRequests(@PathVariable String status) {
        return ResponseEntity.ok(attendanceService.getLeaveRequests(status));
    }

        // --- Approve/Reject Leave ---
        @PatchMapping("/{id}/leave/{status}")
        public ResponseEntity<AttendanceResponseDto> approveLeave(@PathVariable Long id,
            @PathVariable String status) {
        return ResponseEntity.ok(attendanceService.approveLeave(id, status));
    }

        // --- Delete Attendance ---
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
            attendanceService.deleteAttendance(id);
            return ResponseEntity.noContent().build();
        }
        @PostMapping("/bulk")
    public ResponseEntity<List<AttendanceResponseDto>> markBulkAttendance(
            @Valid @RequestBody BulkAttendanceRequestDto dto) {
        return ResponseEntity.ok(attendanceService.markBulkAttendance(dto));
    }

}

