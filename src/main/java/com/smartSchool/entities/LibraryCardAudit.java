package com.smartSchool.entities;


import com.smartSchool.enums.LibraryCardStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "library_card_audit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibraryCardAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;
    private String studentName;

    private String libraryCardNo;

    @Enumerated(EnumType.STRING)
    private LibraryCardStatus status;

    private Long librarianId;   // who performed the action
    private String librarianName;

    private LocalDateTime actionTime;

    private String action; // e.g. "ASSIGN_CARD", "UPDATE_STATUS", "SEARCH"
}
