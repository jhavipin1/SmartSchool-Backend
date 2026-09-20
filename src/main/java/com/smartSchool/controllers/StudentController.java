package com.smartSchool.controllers;

import com.smartSchool.dtos.student.StudentRequestDto;
import com.smartSchool.dtos.student.StudentResponseDto;
import com.smartSchool.enums.Gender;
import com.smartSchool.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // ==========================================
    // 1. CREATE ENDPOINTS
    // ==========================================

    @PostMapping
    public ResponseEntity<StudentResponseDto> createStudent(@Valid @RequestBody StudentRequestDto dto) {
        StudentResponseDto response = studentService.createStudent(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<StudentResponseDto>> createStudentsBulk(@Valid @RequestBody List<StudentRequestDto> dtos) {
        List<StudentResponseDto> responses = studentService.createStudentsBulk(dtos);
        return new ResponseEntity<>(responses, HttpStatus.CREATED);
    }

    // ==========================================
    // 2. READ & SEARCH ENDPOINTS
    // ==========================================

    @GetMapping
    public ResponseEntity<Page<StudentResponseDto>> getAllStudents(
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(studentService.getAllStudents(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping("/admission/{admissionNumber}")
    public ResponseEntity<StudentResponseDto> getStudentByAdmissionNumber(@PathVariable String admissionNumber) {
        return ResponseEntity.ok(studentService.getStudentByAdmissionNumber(admissionNumber));
    }

    @GetMapping("/roll-number/{rollNumber}")
    public ResponseEntity<StudentResponseDto> getStudentByRollNumber(@PathVariable String rollNumber) {
        return ResponseEntity.ok(studentService.getStudentByRollNumber(rollNumber));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<StudentResponseDto>> filterStudents(
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) Long sectionId,
            @RequestParam(required = false) Gender gender,
            Pageable pageable) {
        return ResponseEntity.ok(studentService.filterStudents(classId, sectionId, gender, pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<StudentResponseDto>> searchStudents(
            @RequestParam("query") String query,
            Pageable pageable) {
        return ResponseEntity.ok(studentService.searchStudents(query, pageable));
    }

    // ==========================================
    // 3. UPDATE ENDPOINTS
    // ==========================================

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDto> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequestDto dto) {
        return ResponseEntity.ok(studentService.updateStudent(id, dto));
    }

    @PatchMapping("/{id}/reassign-class")
    public ResponseEntity<StudentResponseDto> reassignClassAndSection(
            @PathVariable Long id,
            @RequestParam Long newClassId,
            @RequestParam Long newSectionId) {
        return ResponseEntity.ok(studentService.reassignClassAndSection(id, newClassId, newSectionId));
    }

    // ==========================================
    // 4. DELETE ENDPOINTS
    // ==========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}