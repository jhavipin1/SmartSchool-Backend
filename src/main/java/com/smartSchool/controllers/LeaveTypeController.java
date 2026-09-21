package com.smartSchool.controllers;

import com.smartSchool.dtos.staff.LeaveTypeDTO;
import com.smartSchool.services.LeaveTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-types")
@RequiredArgsConstructor
public class LeaveTypeController {

    private final LeaveTypeService service;

    @PostMapping
    public ResponseEntity<LeaveTypeDTO> create(@RequestBody LeaveTypeDTO dto) {
        return ResponseEntity.ok(service.createLeaveType(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveTypeDTO> update(@PathVariable Long id, @RequestBody LeaveTypeDTO dto) {
        return ResponseEntity.ok(service.updateLeaveType(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteLeaveType(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<LeaveTypeDTO>> getAll() {
        return ResponseEntity.ok(service.getAllLeaveTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveTypeDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getLeaveTypeById(id));
    }
}

