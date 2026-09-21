package com.smartSchool.services;

import com.smartSchool.dtos.staff.StaffLeaveDTO;

import java.util.List;

public interface StaffLeaveService {
    StaffLeaveDTO applyLeave(StaffLeaveDTO dto);
    StaffLeaveDTO approveLeave(Long id);
    StaffLeaveDTO rejectLeave(Long id);
    List<StaffLeaveDTO> getLeavesByStaff(Long staffId);
    List<StaffLeaveDTO> getAllLeaves();
}
