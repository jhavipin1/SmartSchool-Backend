package com.smartSchool.controllers;

import com.smartSchool.dtos.exam.*;
import com.smartSchool.services.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping("/group")
    public ResponseEntity<ExamGroupDTO> createGroup(@RequestBody ExamGroupDTO dto) {
        return ResponseEntity.ok(examService.createExamGroup(dto));
    }

    @PostMapping("/type")
    public ResponseEntity<ExamTypeDTO> createType(@RequestBody ExamTypeDTO dto) {
        return ResponseEntity.ok(examService.createExamType(dto));
    }

    @PostMapping("/subject")
    public ResponseEntity<ExamSubjectDTO> assignSubject(@RequestBody ExamSubjectDTO dto) {
        return ResponseEntity.ok(examService.assignSubject(dto));
    }

    @PostMapping("/marks")
    public ResponseEntity<StudentExamDTO> recordMarks(@RequestBody StudentExamDTO dto) {
        return ResponseEntity.ok(examService.recordStudentMarks(dto));
    }

    @GetMapping("/results/{classId}/{subjectId}")
    public ResponseEntity<List<StudentExamDTO>> getResultsByClassAndSubject(
            @PathVariable Long classId,
            @PathVariable Long subjectId) {
        List<StudentExamDTO> results = examService.getResultsByClassAndSubject(classId, subjectId);
        return ResponseEntity.ok(results);
    }
    @GetMapping("/summary/{examTypeId}/{classId}/{subjectId}")
    public ResponseEntity<ExamTypeSummaryDTO> getExamTypeSummary(
            @PathVariable Long examTypeId,
            @PathVariable Long classId,
            @PathVariable Long subjectId) {
        return ResponseEntity.ok(examService.getExamTypeSummary(examTypeId, classId, subjectId));
    }


}
