package com.smartSchool.repositories;

import com.smartSchool.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByStaffId(Long staffId);
    boolean existsByStaffId(Long staffId);
}