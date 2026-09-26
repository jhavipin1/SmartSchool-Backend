package com.smartSchool.controllers;

import com.smartSchool.dtos.exam.*;
import com.smartSchool.services.ExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    // --- EXAM GROUP ENDPOINTS ---
    @PostMapping("/groups")
    public ResponseEntity<ExamGroupDTO> createGroup(@Valid @RequestBody ExamGroupDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(examService.createExamGroup(dto));
    }

    @GetMapping("/groups")
    public ResponseEntity<List<ExamGroupDTO>> getAllGroups() {
        return ResponseEntity.ok(examService.getAllExamGroups());
    }

    @GetMapping("/groups/{id}")
    public ResponseEntity<ExamGroupDTO> getGroupById(@PathVariable Long id) {
        return ResponseEntity.ok(examService.getExamGroupById(id));
    }

    @DeleteMapping("/groups/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        examService.deleteExamGroup(id);
        return ResponseEntity.noContent().build();
    }

    // --- EXAM TYPE ENDPOINTS ---
    @PostMapping("/types")
    public ResponseEntity<ExamTypeDTO> createType(@Valid @RequestBody ExamTypeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(examService.createExamType(dto));
    }

    @GetMapping("/types/group/{groupId}")
    public ResponseEntity<List<ExamTypeDTO>> getTypesByGroupId(@PathVariable Long groupId) {
        return ResponseEntity.ok(examService.getExamTypesByGroupId(groupId));
    }

    // --- EXAM SUBJECT MAPPING ENDPOINTS ---
    @PostMapping("/subjects")
    public ResponseEntity<ExamSubjectDTO> assignSubject(@Valid @RequestBody ExamSubjectDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(examService.assignSubject(dto));
    }

    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<Void> deleteExamSubject(@PathVariable Long id) {
        examService.deleteExamSubject(id);
        return ResponseEntity.noContent().build();
    }

    // --- MARKS & RESULTS ENDPOINTS ---
    @PostMapping("/marks")
    public ResponseEntity<StudentExamDTO> recordMarks(@Valid @RequestBody StudentExamDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(examService.recordStudentMarks(dto));
    }

    @PostMapping("/marks/bulk")
    public ResponseEntity<List<StudentExamDTO>> recordBulkMarks(@Valid @RequestBody List<StudentExamDTO> dtos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(examService.recordBulkStudentMarks(dtos));
    }

    @GetMapping("/results/{classId}/{subjectId}")
    public ResponseEntity<List<StudentExamDTO>> getResultsByClassAndSubject(
            @PathVariable Long classId,
            @PathVariable Long subjectId) {
        return ResponseEntity.ok(examService.getResultsByClassAndSubject(classId, subjectId));
    }

    @GetMapping("/summary/{examTypeId}/{classId}/{subjectId}")
    public ResponseEntity<ExamTypeSummaryDTO> getExamTypeSummary(
            @PathVariable Long examTypeId,
            @PathVariable Long classId,
            @PathVariable Long subjectId) {
        return ResponseEntity.ok(examService.getExamTypeSummary(examTypeId, classId, subjectId));
    }


    // --- BULK / FULL SETUP ENDPOINTS ---
    @PostMapping("/setup")
    public ResponseEntity<ExamSubjectDTO> createFullExamSetup(@Valid @RequestBody ExamSetupRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(examService.createFullExamSetup(dto));
    }

    @PostMapping("/setup/bulk")
    public ResponseEntity<List<ExamSubjectDTO>> createBulkExamSetup(@Valid @RequestBody ExamSetupBulkRequestDto bulkDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(examService.createBulkExamSetup(bulkDto));
    }
}