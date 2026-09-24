package com.smartSchool.controllers;

import com.smartSchool.dtos.className.BulkClassNameRequestDto;
import com.smartSchool.dtos.className.ClassNameRequestDto;
import com.smartSchool.dtos.className.ClassNameResponseDto;
import com.smartSchool.dtos.className.ClassWithSectionsRequestDto;
import com.smartSchool.services.ClassNameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassNameController {

    private final ClassNameService classNameService;

    public ClassNameController(ClassNameService classNameService) {
        this.classNameService = classNameService;
    }

    @PostMapping
    public ResponseEntity<ClassNameResponseDto> createClass(@RequestBody ClassNameRequestDto dto) {
        return ResponseEntity.ok(classNameService.createClass(dto));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<ClassNameResponseDto>> createClassesBulk(@RequestBody BulkClassNameRequestDto dto) {
        return ResponseEntity.ok(classNameService.createClassesBulk(dto.getClasses()));
    }

    @GetMapping
    public ResponseEntity<List<ClassNameResponseDto>> getAllClasses() {
        return ResponseEntity.ok(classNameService.getAllClasses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassNameResponseDto> getClassById(@PathVariable Long id) {
        return ResponseEntity.ok(classNameService.getClassById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassNameResponseDto> updateClass(@PathVariable Long id, @RequestBody ClassNameRequestDto dto) {
        return ResponseEntity.ok(classNameService.updateClass(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        classNameService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/class-with-sections")
    public ResponseEntity<ClassNameResponseDto> createClassWithSections(
            @RequestBody ClassWithSectionsRequestDto dto) {
        return ResponseEntity.ok(classNameService.createClassWithSections(dto));
    }

}

