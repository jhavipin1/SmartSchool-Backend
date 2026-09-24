package com.smartSchool.services.impl;

import com.smartSchool.dtos.staff.StaffRequestDto;
import com.smartSchool.dtos.staff.StaffResponseDto;
import com.smartSchool.dtos.staff.StaffStatusUpdateDto;
import com.smartSchool.entities.*;
import com.smartSchool.repositories.*;
import com.smartSchool.services.StaffService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository; // Injected TeacherRepository

    @Override
    @Transactional
    public StaffResponseDto createStaff(StaffRequestDto dto) {
        Staff staff = mapToEntity(dto);
        Staff savedStaff = staffRepository.save(staff);

        // Check if designation or role indicates Teacher
        if (isTeacher(savedStaff)) {
            createTeacherRecordIfNotExists(savedStaff);
        }

        return mapToResponse(savedStaff);
    }

    @Override
    @Transactional
    public StaffResponseDto updateStaff(Long id, StaffRequestDto dto) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        staff.setFirstName(dto.getFirstName());
        staff.setLastName(dto.getLastName());
        staff.setPhone(dto.getPhone());
        staff.setJoiningDate(dto.getJoiningDate());
        staff.setDepartment(departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found")));
        staff.setDesignation(designationRepository.findById(dto.getDesignationId())
                .orElseThrow(() -> new RuntimeException("Designation not found")));
        staff.setUser(userRepository.findById(Math.toIntExact(dto.getUserId()))
                .orElseThrow(() -> new RuntimeException("User not found")));

        Staff updatedStaff = staffRepository.save(staff);

        // Check if updated designation/role requires a Teacher entity
        if (isTeacher(updatedStaff)) {
            createTeacherRecordIfNotExists(updatedStaff);
        }

        return mapToResponse(updatedStaff);
    }

    @Override
    public StaffResponseDto getStaffById(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        return mapToResponse(staff);
    }

    @Override
    public List<StaffResponseDto> getAllStaff() {
        return staffRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }

    @Override
    @Transactional
    public StaffResponseDto updateStaffStatus(Long id, StaffStatusUpdateDto statusUpdateDto) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        staff.getUser().setActive(statusUpdateDto.getActive());
        return mapToResponse(staffRepository.save(staff));
    }

    // Helper method to determine if Staff is a Teacher
    private boolean isTeacher(Staff staff) {
        if (staff.getDesignation() != null && staff.getDesignation().getDesignationName() != null) {
            return staff.getDesignation().getDesignationName().equalsIgnoreCase("Teacher");
        }
        return false;
    }

    // Helper method to create Teacher record idempotently
    private void createTeacherRecordIfNotExists(Staff staff) {
        boolean teacherExists = teacherRepository.existsByStaffId(staff.getId());
        if (!teacherExists) {
            Teacher teacher = Teacher.builder()
                    .staff(staff)
                    .subjects(Collections.emptyList())
                    .build();
            teacherRepository.save(teacher);
        }
    }

    private StaffResponseDto mapToResponse(Staff staff) {
        return StaffResponseDto.builder()
                .id(staff.getId())
                .employeeId(staff.getEmployeeId())
                .firstName(staff.getFirstName())
                .lastName(staff.getLastName())
                .phone(staff.getPhone())
                .joiningDate(staff.getJoiningDate())
                .departmentName(staff.getDepartment().getDepartmentName())
                .designationName(staff.getDesignation().getDesignationName())
                .username(staff.getUser().getUsername())
                .active(staff.getUser().isActive())
                .build();
    }

    private Staff mapToEntity(StaffRequestDto dto) {
        return Staff.builder()
                .employeeId(dto.getEmployeeId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phone(dto.getPhone())
                .joiningDate(dto.getJoiningDate())
                .department(departmentRepository.findById(dto.getDepartmentId())
                        .orElseThrow(() -> new RuntimeException("Department not found")))
                .designation(designationRepository.findById(dto.getDesignationId())
                        .orElseThrow(() -> new RuntimeException("Designation not found")))
                .user(userRepository.findById(Math.toIntExact(dto.getUserId()))
                        .orElseThrow(() -> new RuntimeException("User not found")))
                .build();
    }
}