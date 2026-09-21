package com.smartSchool.controllers;


import com.smartSchool.dtos.staff.StaffLeaveDTO;
import com.smartSchool.services.StaffLeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff-leaves")
@RequiredArgsConstructor
public class StaffLeaveController {

    private final StaffLeaveService service;

    @PostMapping
    public ResponseEntity<StaffLeaveDTO> applyLeave(@RequestBody StaffLeaveDTO dto) {
        return ResponseEntity.ok(service.applyLeave(dto));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<StaffLeaveDTO> approveLeave(@PathVariable Long id) {
        return ResponseEntity.ok(service.approveLeave(id));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<StaffLeaveDTO> rejectLeave(@PathVariable Long id) {
        return ResponseEntity.ok(service.rejectLeave(id));
    }

    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<StaffLeaveDTO>> getLeavesByStaff(@PathVariable Long staffId) {
        return ResponseEntity.ok(service.getLeavesByStaff(staffId));
    }

    @GetMapping
    public ResponseEntity<List<StaffLeaveDTO>> getAllLeaves() {
        return ResponseEntity.ok(service.getAllLeaves());
    }
}
