package com.smartSchool.controllers;

import com.smartSchool.dtos.staff.StaffAttendanceDTO;
import com.smartSchool.services.StaffAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/staff-attendance")
@RequiredArgsConstructor
public class StaffAttendanceController {

    private final StaffAttendanceService service;

    @PostMapping
    public ResponseEntity<StaffAttendanceDTO> markAttendance(@RequestBody StaffAttendanceDTO dto) {
        return ResponseEntity.ok(service.markAttendance(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffAttendanceDTO> updateAttendance(@PathVariable Long id, @RequestBody StaffAttendanceDTO dto) {
        return ResponseEntity.ok(service.updateAttendance(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        service.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<StaffAttendanceDTO>> getAttendanceByStaff(
            @PathVariable Long staffId,
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {
        return ResponseEntity.ok(service.getAttendanceByStaff(staffId, start, end));
    }

    @GetMapping
    public ResponseEntity<List<StaffAttendanceDTO>> getAllAttendance() {
        return ResponseEntity.ok(service.getAllAttendance());
    }
}
