package com.smartSchool.controllers;

import com.smartSchool.dtos.subject.*;
import com.smartSchool.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<SubjectResponseDto> createSubject(@Valid @RequestBody SubjectRequestDto dto) {
        return ResponseEntity.ok(subjectService.createSubject(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectResponseDto> updateSubject(@PathVariable Long id,
                                                            @Valid @RequestBody SubjectRequestDto dto) {
        return ResponseEntity.ok(subjectService.updateSubject(id, dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<SubjectResponseDto> updateStatus(@PathVariable Long id,
                                                           @Valid @RequestBody SubjectStatusUpdateDto dto) {
        return ResponseEntity.ok(subjectService.updateSubjectStatus(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponseDto> getSubject(@PathVariable Long id) {
        return ResponseEntity.ok(subjectService.getSubjectById(id));
    }

    @GetMapping
    public ResponseEntity<List<SubjectResponseDto>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }
}

