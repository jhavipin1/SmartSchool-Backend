package com.smartSchool.controllers;

import com.smartSchool.dtos.library.RackDto;
import com.smartSchool.services.RackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/racks")
@RequiredArgsConstructor
public class RackController {

    private final RackService rackService;

    @PostMapping
    public RackDto addRack(@RequestBody RackDto dto) {
        return rackService.addRack(dto);
    }

    @PutMapping("/{id}")
    public RackDto updateRack(@PathVariable String rackCode, @RequestBody RackDto dto) {
        return rackService.updateRack(rackCode, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteRack(@PathVariable String rackCode) {
        rackService.deleteRack(rackCode);
    }

    @GetMapping("/search")
    public Page<RackDto> searchRacks(@RequestParam(required = false) String rackCode,
                                     @RequestParam(required = false) String location,
                                     Pageable pageable) {
        return rackService.searchRacks(rackCode, location, pageable);
    }
}

