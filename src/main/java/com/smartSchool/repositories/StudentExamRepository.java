package com.smartSchool.repositories;

import com.smartSchool.entities.StudentExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentExamRepository extends JpaRepository<StudentExam, Long> {

    // 1. Fetch existing mark entry for a student and subject
    Optional<StudentExam> findByStudent_IdAndSubject_Id(Long studentId, Long subjectId);

    // 2. Fetch all marks recorded for a specific student
    List<StudentExam> findByStudent_Id(Long studentId);

    // 3. Results filtered by Class + Section + Subject
    List<StudentExam> findBySubject_ClassName_IdAndSubject_Section_IdAndSubject_Id(
            Long classId, Long sectionId, Long subjectId);

    // 4. Results filtered by ExamType + Class + Section + Subject
    List<StudentExam> findBySubject_ExamType_IdAndSubject_ClassName_IdAndSubject_Section_IdAndSubject_Id(
            Long examTypeId, Long classId, Long sectionId, Long subjectId);

    // 5. Fetch all student results for an Exam Type and Class across subjects
    List<StudentExam> findBySubject_ExamType_IdAndSubject_ClassName_Id(Long examTypeId, Long classId);

    // 6. Check if student marks have already been recorded for a subject
    boolean existsByStudent_IdAndSubject_Id(Long studentId, Long subjectId);

    // 7. Optimized JPQL Query fetching full student results with JOIN FETCH (Prevents N+1 SELECT issues)
    @Query("SELECT se FROM StudentExam se " +
            "JOIN FETCH se.student s " +
            "JOIN FETCH se.subject sub " +
            "LEFT JOIN FETCH sub.className " +
            "LEFT JOIN FETCH sub.section " +
            "LEFT JOIN FETCH sub.examType " +
            "WHERE sub.examType.id = :examTypeId " +
            "AND sub.className.id = :classId " +
            "AND sub.section.id = :sectionId")
    List<StudentExam> findFullResultsByExamTypeClassAndSection(
            @Param("examTypeId") Long examTypeId,
            @Param("classId") Long classId,
            @Param("sectionId") Long sectionId);
}