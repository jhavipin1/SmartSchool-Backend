package com.smartSchool.services.impl;

import com.smartSchool.dtos.library.RackDto;
import com.smartSchool.dtos.library.RackSpecification;
import com.smartSchool.entities.Rack;
import com.smartSchool.repositories.RackRepository;
import com.smartSchool.services.RackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RackServiceImpl implements RackService {

    private final RackRepository rackRepository;

    @Override
    public RackDto addRack(RackDto dto) {
        Rack rack = toEntity(dto);
        Rack saved = rackRepository.save(rack);
        return toDto(saved);
    }

    @Override
    public RackDto updateRack(Long id, RackDto dto) {
        Rack rack = rackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rack not found"));

        rack.setRackCode(dto.getRackCode());
        rack.setLocation(dto.getLocation());

        Rack updated = rackRepository.save(rack);
        return toDto(updated);
    }

    @Override
    public void deleteRack(Long id) {
        rackRepository.deleteById(id);
    }

    @Override
    public Page<RackDto> searchRacks(String rackCode, String location, Pageable pageable) {
        Specification<Rack> spec = Specification.where(RackSpecification.hasRackCode(rackCode))
                .and(RackSpecification.hasLocation(location));

        Page<Rack> racks = rackRepository.findAll(spec, pageable);
        return racks.map(this::toDto);
    }

    // ✅ Manual mapping methods
    private Rack toEntity(RackDto dto) {
        Rack rack = new Rack();
        rack.setRackCode(dto.getRackCode());
        rack.setLocation(dto.getLocation());
        return rack;
    }

    private RackDto toDto(Rack rack) {
        RackDto dto = new RackDto();
        dto.setId(rack.getId());
        dto.setRackCode(rack.getRackCode());
        dto.setLocation(rack.getLocation());
        return dto;
    }
}
