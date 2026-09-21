package com.smartSchool.controllers;

import com.smartSchool.dtos.library.AuditFilterRequestDto;
import com.smartSchool.entities.LibraryCardAudit;
import com.smartSchool.services.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/library/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @PostMapping("/filter")
    public ResponseEntity<List<LibraryCardAudit>> filterAuditLogs(@RequestBody AuditFilterRequestDto dto) {
        return ResponseEntity.ok(auditService.filterAuditLogs(dto));
    }
}
