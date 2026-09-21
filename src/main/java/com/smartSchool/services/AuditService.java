package com.smartSchool.services;

import com.smartSchool.dtos.library.AuditFilterRequestDto;
import com.smartSchool.entities.LibraryCardAudit;

import java.util.List;

public interface AuditService {
    List<LibraryCardAudit> filterAuditLogs(AuditFilterRequestDto dto);
}

