package com.smartSchool.services.impl;

import com.smartSchool.dtos.library.AuditFilterRequestDto;
import com.smartSchool.entities.LibraryCardAudit;
import com.smartSchool.repositories.LibraryCardAuditRepository;
import com.smartSchool.services.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final LibraryCardAuditRepository auditRepository;

    @Override
    public List<LibraryCardAudit> filterAuditLogs(AuditFilterRequestDto dto) {
        if (dto.getLibrarianName() != null) {
            return auditRepository.findByLibrarianNameContainingIgnoreCase(dto.getLibrarianName());
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            return auditRepository.findByActionTimeBetween(dto.getStartDate(), dto.getEndDate());
        }
        if (dto.getAction() != null) {
            return auditRepository.findByAction(dto.getAction());
        }
        return auditRepository.findAll();
    }
}
