package com.smartSchool.repositories;

import com.smartSchool.entities.Fee;
import com.smartSchool.enums.FeePaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Long> {

    List<Fee> findByStudentId(Long studentId);

    List<Fee> findByStatus(FeePaymentStatus status);

    List<Fee> findByStudentIdAndStatus(Long studentId, FeePaymentStatus status);

    @Query("SELECT f FROM Fee f WHERE f.student.className.id = :classId")
    List<Fee> findByClassId(@Param("classId") Long classId);
}
