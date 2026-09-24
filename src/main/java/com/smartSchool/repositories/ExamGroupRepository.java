package com.smartSchool.repositories;

import com.smartSchool.entities.ExamGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExamGroupRepository extends JpaRepository<ExamGroup, Long> {

    Optional<ExamGroup> findByName(String name);

    boolean existsByName(String name);
}