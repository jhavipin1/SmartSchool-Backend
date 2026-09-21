package com.smartSchool.repositories;

import com.smartSchool.entities.IssueRecord;
import com.smartSchool.enums.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRecordRepository extends JpaRepository<IssueRecord, Long> {

    // Find all issues for a student by library card number
    List<IssueRecord> findByLibraryCardNo(String libraryCardNo);

    // Find active issued books (not yet returned)
    List<IssueRecord> findByLibraryCardNoAndStatus(String libraryCardNo, IssueStatus status);
}
