package com.smartSchool.dtos.library;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AuditFilterRequestDto {
    private String librarianName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String action; // ASSIGN_CARD, UPDATE_STATUS, SEARCH
}

