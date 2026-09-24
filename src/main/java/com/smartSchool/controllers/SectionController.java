package com.smartSchool.controllers;

import com.smartSchool.dtos.section.SectionRequestDto;
import com.smartSchool.dtos.section.SectionResponseDto;
import com.smartSchool.services.SectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sections")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping
    public ResponseEntity<SectionResponseDto> createSection(@RequestBody SectionRequestDto dto) {
        return ResponseEntity.ok(sectionService.createSection(dto));
    }

    @GetMapping("/class/{classId}")
    public ResponseEntity<List<SectionResponseDto>> getSectionsByClass(@PathVariable Long classId) {
        return ResponseEntity.ok(sectionService.getSectionsByClass(classId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SectionResponseDto> updateSection(@PathVariable Long id, @RequestBody SectionRequestDto dto) {
        return ResponseEntity.ok(sectionService.updateSection(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSection(@PathVariable Long id) {
        sectionService.deleteSection(id);
        return ResponseEntity.noContent().build();
    }
}

