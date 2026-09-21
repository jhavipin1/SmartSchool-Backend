package com.smartSchool.repositories;

import com.smartSchool.entities.StaffAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StaffAttendanceRepository extends JpaRepository<StaffAttendance, Long> {
    List<StaffAttendance> findByStaffIdAndAttendanceDateBetween(Long staffId, LocalDate start, LocalDate end);
}
