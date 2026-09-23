package com.smartSchool.repositories;

import com.smartSchool.entities.StudentExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentExamRepository extends JpaRepository<StudentExam, Long> {
    List<StudentExam> findByExamSubject_ClassName_IdAndExamSubject_Subject_Id(Long classId, Long subjectId);

    List<StudentExam> findByExamSubject_ExamType_IdAndExamSubject_ClassName_IdAndExamSubject_Subject_Id(
            Long examTypeId, Long classId, Long subjectId);
}