package com.smartSchool.repositories;

import com.smartSchool.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    // Check availability by subject code
    boolean existsByCode(String code);

    // Find subject by name
    Optional<Subject> findByName(String name);

    // Find active subjects
    List<Subject> findByActiveTrue();

    // Fetch subjects assigned to a specific ExamType
    List<Subject> findByExamType_Id(Long examTypeId);

    // Fetch subjects assigned to a specific Class and Section
    List<Subject> findByClassName_IdAndSection_Id(Long classId, Long sectionId);

    // Fetch subjects filtered by ExamType, Class, and Section
    List<Subject> findByExamType_IdAndClassName_IdAndSection_Id(
            Long examTypeId, Long classId, Long sectionId);

    // Find a specific subject mapping by ID, ExamType, Class, and Section
    Optional<Subject> findByIdAndExamType_IdAndClassName_IdAndSection_Id(
            Long id, Long examTypeId, Long classId, Long sectionId);

    // Check if subject mapping exists for an ExamType, Class, Section, and Name
    boolean existsByNameAndExamType_IdAndClassName_IdAndSection_Id(
            String name, Long examTypeId, Long classId, Long sectionId);
}