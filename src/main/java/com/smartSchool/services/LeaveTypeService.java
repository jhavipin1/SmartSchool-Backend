package com.smartSchool.services;


import com.smartSchool.dtos.staff.LeaveTypeDTO;

import java.util.List;

public interface LeaveTypeService {
    LeaveTypeDTO createLeaveType(LeaveTypeDTO dto);
    LeaveTypeDTO updateLeaveType(Long id, LeaveTypeDTO dto);
    void deleteLeaveType(Long id);
    List<LeaveTypeDTO> getAllLeaveTypes();
    LeaveTypeDTO getLeaveTypeById(Long id);
}
