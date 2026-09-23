package com.smartSchool.repositories;

import com.smartSchool.entities.ExamGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamGroupRepository extends JpaRepository<ExamGroup, Long> {}