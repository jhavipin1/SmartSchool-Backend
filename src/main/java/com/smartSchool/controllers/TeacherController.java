package com.smartSchool.controllers;

import com.smartSchool.dtos.homework.HomeworkResponseDto;
import com.smartSchool.dtos.homework.TeacherHomeworkRequestDto;
import com.smartSchool.dtos.teacher.TeacherRequestDto;
import com.smartSchool.dtos.teacher.TeacherResponseDto;
import com.smartSchool.services.TeacherService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    public ResponseEntity<TeacherResponseDto> createTeacher(@Valid @RequestBody TeacherRequestDto teacherRequestDto) {
        return new ResponseEntity<>(teacherService.createTeacher(teacherRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> getTeacherById(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @GetMapping
    public ResponseEntity<List<TeacherResponseDto>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @GetMapping("/staff/{staffId}")
    public ResponseEntity<TeacherResponseDto> getTeacherByStaffId(@PathVariable Long staffId) {
        return ResponseEntity.ok(teacherService.getTeacherByStaffId(staffId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }

    // Homework Management Endpoints for Teachers

    @PostMapping("/homework")
    public ResponseEntity<HomeworkResponseDto> assignHomework(@Valid @RequestBody TeacherHomeworkRequestDto homeworkDto) {
        return new ResponseEntity<>(teacherService.assignHomework(homeworkDto), HttpStatus.CREATED);
    }

    @GetMapping("/{teacherId}/homework")
    public ResponseEntity<List<HomeworkResponseDto>> getHomeworksByTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(teacherService.getHomeworksByTeacher(teacherId));
    }
}