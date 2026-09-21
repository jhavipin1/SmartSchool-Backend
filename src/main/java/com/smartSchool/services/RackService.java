package com.smartSchool.services;

import com.smartSchool.dtos.library.RackDto;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;


public interface RackService {
    RackDto addRack(RackDto dto);
    RackDto updateRack(Long id, RackDto dto);
    void deleteRack(Long id);
    Page<RackDto> searchRacks(String rackCode, String location, Pageable pageable);
}