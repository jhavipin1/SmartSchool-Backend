package com.smartSchool.controllers;

import com.smartSchool.dtos.library.LibraryCardUpdateRequestDto;
import com.smartSchool.dtos.library.StudentSearchRequestDto;
import com.smartSchool.dtos.student.StudentResponseDto;
import com.smartSchool.entities.LibraryCardAudit;
import com.smartSchool.repositories.LibraryCardAuditRepository;
import com.smartSchool.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library/students")
@RequiredArgsConstructor
public class LibraryStudentController {

    private final StudentService studentService;
    private final LibraryCardAuditRepository auditRepository;

    @PostMapping("/search")
    public ResponseEntity<List<StudentResponseDto>> searchStudents(@RequestBody StudentSearchRequestDto dto) {
        return ResponseEntity.ok(studentService.searchStudents(dto));
    }

    @PatchMapping("/{id}/library-card")
    public ResponseEntity<StudentResponseDto> assignLibraryCard(@PathVariable Long id,
                                                                @Valid @RequestBody LibraryCardUpdateRequestDto dto) {
        return ResponseEntity.ok(studentService.assignLibraryCard(id, dto));
    }
    @GetMapping("/{id}/audit")
    public ResponseEntity<List<LibraryCardAudit>> getAuditLogs(@PathVariable Long id) {
        return ResponseEntity.ok(auditRepository.findByStudentId(id));
    }
}

