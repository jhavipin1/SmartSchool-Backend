package com.smartSchool.repositories;

import com.smartSchool.entities.LibraryCardAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LibraryCardAuditRepository extends JpaRepository<LibraryCardAudit, Long> {
    List<LibraryCardAudit> findByStudentId(Long studentId);

    List<LibraryCardAudit> findByLibrarianNameContainingIgnoreCase(String librarianName);

    List<LibraryCardAudit> findByActionTimeBetween(LocalDateTime start, LocalDateTime end);

    List<LibraryCardAudit> findByAction(String action);
}
