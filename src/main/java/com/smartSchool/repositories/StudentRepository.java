// StudentRepository.java
package com.smartSchool.repositories;

import com.smartSchool.entities.Student;
import com.smartSchool.enums.Gender;
import com.smartSchool.enums.LibraryCardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByAdmissionNumber(String admissionNumber);

    Optional<Student> findByRollNumber(String rollNumber);

    boolean existsByAdmissionNumber(String admissionNumber);

    boolean existsByRollNumber(String rollNumber);

    boolean existsByEmail(String email);

    boolean existsByUserId(Long userId);

    Page<Student> findByClassName_IdAndSection_Id(Long classId, Long sectionId, Pageable pageable);

    Page<Student> findByClassName_Id(Long classId, Pageable pageable);

    /** Filter students dynamically based on Class, Section, and Gender */
    @Query("SELECT s FROM Student s WHERE " +
            "(:classId IS NULL OR s.className.id = :classId) AND " +
            "(:sectionId IS NULL OR s.section.id = :sectionId) AND " +
            "(:gender IS NULL OR s.gender = :gender)")
    Page<Student> filterStudents(
            @Param("classId") Long classId,
            @Param("sectionId") Long sectionId,
            @Param("gender") Gender gender,
            Pageable pageable
    );

    /** Global search by name, admission number, roll number, or email */
    @Query("SELECT s FROM Student s WHERE " +
            "LOWER(s.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(s.admissionNumber) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(s.rollNumber) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(s.email) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Student> searchStudents(@Param("query") String query, Pageable pageable);

    List<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

    // Find student by library card number
    Optional<Student> findByLibraryCardNo(String libraryCardNo);

    // Find student by library card number AND status
    Optional<Student> findByLibraryCardNoAndLibraryCardStatus(String libraryCardNo, LibraryCardStatus status);
}