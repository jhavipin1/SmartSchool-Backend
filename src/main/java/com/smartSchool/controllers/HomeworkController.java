package com.smartSchool.controllers;

import com.smartSchool.dtos.homework.*;
import com.smartSchool.services.HomeworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/homeworks")
@RequiredArgsConstructor
public class HomeworkController {

    private final HomeworkService homeworkService;

    @PostMapping
    public ResponseEntity<HomeworkResponseDto> createHomework(@Valid @RequestBody HomeworkRequestDto dto) {
        return ResponseEntity.ok(homeworkService.createHomework(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HomeworkResponseDto> updateHomework(@PathVariable Long id,
                                                              @Valid @RequestBody HomeworkRequestDto dto) {
        return ResponseEntity.ok(homeworkService.updateHomework(id, dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<HomeworkResponseDto> updateStatus(@PathVariable Long id,
                                                            @Valid @RequestBody HomeworkStatusUpdateDto dto) {
        return ResponseEntity.ok(homeworkService.updateHomeworkStatus(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HomeworkResponseDto> getHomework(@PathVariable Long id) {
        return ResponseEntity.ok(homeworkService.getHomeworkById(id));
    }

    @GetMapping
    public ResponseEntity<List<HomeworkResponseDto>> getAllHomeworks() {
        return ResponseEntity.ok(homeworkService.getAllHomeworks());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHomework(@PathVariable Long id) {
        homeworkService.deleteHomework(id);
        return ResponseEntity.noContent().build();
    }
}
