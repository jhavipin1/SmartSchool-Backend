package com.smartSchool.services.impl;

import com.smartSchool.dtos.staff.StaffLeaveDTO;
import com.smartSchool.entities.Staff;
import com.smartSchool.entities.StaffLeave;
import com.smartSchool.repositories.StaffLeaveRepository;
import com.smartSchool.repositories.StaffRepository;
import com.smartSchool.services.StaffLeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaffLeaveServiceImpl implements StaffLeaveService {

    private final StaffLeaveRepository leaveRepository;
    private final StaffRepository staffRepository;

    @Override
    public StaffLeaveDTO applyLeave(StaffLeaveDTO dto) {
        Staff staff = staffRepository.findById(dto.getStaffId())
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        StaffLeave leave = StaffLeave.builder()
                .leaveType(dto.getLeaveType())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .reason(dto.getReason())
                .status("Pending")
                .staff(staff)
                .build();
        return mapToDTO(leaveRepository.save(leave));
    }

    @Override
    public StaffLeaveDTO approveLeave(Long id) {
        StaffLeave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
        leave.setStatus("Approved");
        return mapToDTO(leaveRepository.save(leave));
    }

    @Override
    public StaffLeaveDTO rejectLeave(Long id) {
        StaffLeave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
        leave.setStatus("Rejected");
        return mapToDTO(leaveRepository.save(leave));
    }

    @Override
    public List<StaffLeaveDTO> getLeavesByStaff(Long staffId) {
        return leaveRepository.findByStaffId(staffId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<StaffLeaveDTO> getAllLeaves() {
        return leaveRepository.findAll()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private StaffLeaveDTO mapToDTO(StaffLeave leave) {
        return StaffLeaveDTO.builder()
                .id(leave.getId())
                .leaveType(leave.getLeaveType())
                .startDate(leave.getStartDate())
                .endDate(leave.getEndDate())
                .reason(leave.getReason())
                .status(leave.getStatus())
                .staffId(leave.getStaff().getId())
                .staffName(leave.getStaff().getFirstName() + " " + leave.getStaff().getLastName())
                .build();
    }
}
