package com.smartSchool.repositories;

import com.smartSchool.entities.ExamSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamSubjectRepository extends JpaRepository<ExamSubject, Long> {}