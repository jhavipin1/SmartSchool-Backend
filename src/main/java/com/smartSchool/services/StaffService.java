package com.smartSchool.services;


import com.smartSchool.dtos.staff.StaffRequestDto;
import com.smartSchool.dtos.staff.StaffResponseDto;
import com.smartSchool.dtos.staff.StaffStatusUpdateDto;

import java.util.List;

public interface StaffService {
    StaffResponseDto createStaff(StaffRequestDto staffRequestDto);
    StaffResponseDto updateStaff(Long id, StaffRequestDto staffRequestDto);
    StaffResponseDto getStaffById(Long id);
    List<StaffResponseDto> getAllStaff();
    void deleteStaff(Long id);
    StaffResponseDto updateStaffStatus(Long id, StaffStatusUpdateDto statusUpdateDto);
}
