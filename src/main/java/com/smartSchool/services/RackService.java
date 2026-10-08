package com.smartSchool.services;

import com.smartSchool.dtos.library.RackDto;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.List;


public interface RackService {
    List<RackDto> getAllRacks();
    RackDto addRack(RackDto dto);
    RackDto updateRack(String rackCode, RackDto dto);
    void deleteRack(String rackCode);
    Page<RackDto> searchRacks(String rackCode, String location, Pageable pageable);
}