package com.smartSchool.services.impl;

import com.smartSchool.dtos.library.RackDto;
import com.smartSchool.dtos.library.RackSpecification;
import com.smartSchool.entities.Rack;
import com.smartSchool.repositories.BookRepository;
import com.smartSchool.repositories.RackRepository;
import com.smartSchool.services.RackService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RackServiceImpl implements RackService {

    private final RackRepository rackRepository;
    private final BookRepository bookRepository;

    @Override
    public RackDto addRack(RackDto dto) {
        Rack rack = toEntity(dto);
        Rack saved = rackRepository.save(rack);
        return toDto(saved);
    }

    @Override
    @Transactional
    public RackDto updateRack(String rackCode, RackDto dto) {
        Rack existingRack = rackRepository.findById(rackCode)
                .orElseThrow(() -> new EntityNotFoundException("Rack not found with code: " + rackCode));

        // If rackCode is being updated/renamed, check if the new rackCode already exists
        if (!existingRack.getRackCode().equalsIgnoreCase(dto.getRackCode())
                && rackRepository.existsById(dto.getRackCode())) {
            throw new IllegalArgumentException("Rack code '" + dto.getRackCode() + "' is already in use.");
        }

        existingRack.setRackCode(dto.getRackCode());
        existingRack.setLocation(dto.getLocation());

        Rack updatedRack = rackRepository.save(existingRack);
        return toDto(updatedRack);
    }

    @Override
    @Transactional
    public void deleteRack(String rackCode) {
        Rack rack = rackRepository.findById(rackCode)
                .orElseThrow(() -> new EntityNotFoundException("Rack not found with code: " + rackCode));

        // Prevent deletion if books are currently mapped to this rack code
        boolean hasAssignedBooks = bookRepository.existsByRackRackCode(rackCode);
        if (hasAssignedBooks) {
            throw new IllegalStateException("Cannot delete Rack '" + rackCode + "'. Books are currently assigned to it.");
        }

        rackRepository.delete(rack);
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
        dto.setRackCode(rack.getRackCode());
        dto.setLocation(rack.getLocation());
        return dto;
    }
}
