package com.smartSchool.services.impl;

import com.smartSchool.dtos.staff.StaffRequestDto;
import com.smartSchool.dtos.staff.StaffResponseDto;
import com.smartSchool.dtos.staff.StaffStatusUpdateDto;
import com.smartSchool.entities.*;
import com.smartSchool.repositories.*;
import com.smartSchool.services.StaffService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;
    private final UserRepository userRepository;

    @Override
    public StaffResponseDto createStaff(StaffRequestDto dto) {
        Staff staff = mapToEntity(dto);
        return mapToResponse(staffRepository.save(staff));
    }

    @Override
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
        return mapToResponse(staffRepository.save(staff));
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
    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }

    @Override
    public StaffResponseDto updateStaffStatus(Long id, StaffStatusUpdateDto statusUpdateDto) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        staff.getUser().setActive(statusUpdateDto.getActive());
        return mapToResponse(staffRepository.save(staff));
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

