package com.smartSchool.controllers;

import com.smartSchool.dtos.library.RackDto;
import com.smartSchool.services.RackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/racks")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class RackController {

    private final RackService rackService;

    // Added: Direct GET /api/racks endpoint for Angular dropdown population
    @GetMapping
    public List<RackDto> getAllRacks() {
        return rackService.getAllRacks(); // Ensure rackService has this method or call search with large page
    }

    @PostMapping
    public RackDto addRack(@RequestBody RackDto dto) {
        return rackService.addRack(dto);
    }

    @PutMapping("/{rackCode}")
    public RackDto updateRack(@PathVariable String rackCode, @RequestBody RackDto dto) {
        return rackService.updateRack(rackCode, dto);
    }

    @DeleteMapping("/{rackCode}")
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