package com.smartSchool.controllers;

import com.smartSchool.dtos.staff.StaffRequestDto;
import com.smartSchool.dtos.staff.StaffResponseDto;
import com.smartSchool.dtos.staff.StaffStatusUpdateDto;
import com.smartSchool.services.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @PostMapping
    public ResponseEntity<StaffResponseDto> createStaff(@Valid @RequestBody StaffRequestDto staffRequestDto) {
        return ResponseEntity.ok(staffService.createStaff(staffRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffResponseDto> updateStaff(@PathVariable Long id, @Valid @RequestBody StaffRequestDto staffRequestDto) {
        return ResponseEntity.ok(staffService.updateStaff(id, staffRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffResponseDto> getStaffById(@PathVariable Long id) {
        return ResponseEntity.ok(staffService.getStaffById(id));
    }

    @GetMapping
    public ResponseEntity<List<StaffResponseDto>> getAllStaff() {
        return ResponseEntity.ok(staffService.getAllStaff());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<StaffResponseDto> updateStaffStatus(@PathVariable Long id, @Valid @RequestBody StaffStatusUpdateDto statusUpdateDto) {
        return ResponseEntity.ok(staffService.updateStaffStatus(id, statusUpdateDto));
    }
}
