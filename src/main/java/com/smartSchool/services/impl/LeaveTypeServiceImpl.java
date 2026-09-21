package com.smartSchool.services.impl;


import com.smartSchool.dtos.staff.LeaveTypeDTO;
import com.smartSchool.entities.LeaveType;
import com.smartSchool.repositories.LeaveTypeRepository;
import com.smartSchool.services.LeaveTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveTypeServiceImpl implements LeaveTypeService {

    private final LeaveTypeRepository repository;

    @Override
    public LeaveTypeDTO createLeaveType(LeaveTypeDTO dto) {
        if (repository.existsByNameIgnoreCase(dto.getName())) {
            throw new RuntimeException("Leave type already exists");
        }
        LeaveType leaveType = LeaveType.builder().name(dto.getName()).build();
        leaveType = repository.save(leaveType);
        return new LeaveTypeDTO(leaveType.getId(), leaveType.getName());
    }

    @Override
    public LeaveTypeDTO updateLeaveType(Long id, LeaveTypeDTO dto) {
        LeaveType leaveType = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave type not found"));
        leaveType.setName(dto.getName());
        leaveType = repository.save(leaveType);
        return new LeaveTypeDTO(leaveType.getId(), leaveType.getName());
    }

    @Override
    public void deleteLeaveType(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<LeaveTypeDTO> getAllLeaveTypes() {
        return repository.findAll()
                .stream()
                .map(l -> new LeaveTypeDTO(l.getId(), l.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public LeaveTypeDTO getLeaveTypeById(Long id) {
        LeaveType leaveType = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave type not found"));
        return new LeaveTypeDTO(leaveType.getId(), leaveType.getName());
    }
}
