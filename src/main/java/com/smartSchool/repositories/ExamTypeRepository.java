package com.smartSchool.repositories;

import com.smartSchool.entities.ExamType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamTypeRepository extends JpaRepository<ExamType, Long> {

    Optional<ExamType> findByTypeNameAndExamGroup_Id(String typeName, Long examGroupId);

    List<ExamType> findByExamGroup_Id(Long examGroupId);
}