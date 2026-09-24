package com.smartSchool.services.impl;

import com.smartSchool.dtos.section.SectionRequestDto;
import com.smartSchool.dtos.section.SectionResponseDto;
import com.smartSchool.entities.ClassName;
import com.smartSchool.entities.Section;
import com.smartSchool.repositories.ClassNameRepository;
import com.smartSchool.repositories.SectionRepository;
import com.smartSchool.services.SectionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;
    private final ClassNameRepository classNameRepository;

    public SectionServiceImpl(SectionRepository sectionRepository, ClassNameRepository classNameRepository) {
        this.sectionRepository = sectionRepository;
        this.classNameRepository = classNameRepository;
    }

    @Override
    public SectionResponseDto createSection(SectionRequestDto dto) {
        ClassName className = classNameRepository.findById(dto.getClassNameId())
                .orElseThrow(() -> new EntityNotFoundException("Class not found"));
        Section section = Section.builder().sectionName(dto.getSectionName()).className(className).build();
        Section saved = sectionRepository.save(section);
        return new SectionResponseDto(saved.getId(), saved.getSectionName(), className.getId());
    }

    @Override
    public List<SectionResponseDto> createSectionsBulk(List<SectionRequestDto> dtos) {
        List<Section> sections = dtos.stream().map(dto -> {
            ClassName className = classNameRepository.findById(dto.getClassNameId())
                    .orElseThrow(() -> new EntityNotFoundException("Class not found with ID: " + dto.getClassNameId()));
            return Section.builder()
                    .sectionName(dto.getSectionName())
                    .className(className)
                    .build();
        }).collect(Collectors.toList());

        List<Section> saved = sectionRepository.saveAll(sections);

        return saved.stream()
                .map(s -> new SectionResponseDto(s.getId(), s.getSectionName(), s.getClassName().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<SectionResponseDto> getSectionsByClass(Long classId) {
        return sectionRepository.findByClassNameId(classId).stream()
                .map(s -> new SectionResponseDto(s.getId(), s.getSectionName(), classId))
                .toList();
    }

    @Override
    public SectionResponseDto updateSection(Long id, SectionRequestDto dto) {
        Section s = sectionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Section not found"));
        s.setSectionName(dto.getSectionName());
        Section updated = sectionRepository.save(s);
        return new SectionResponseDto(updated.getId(), updated.getSectionName(), updated.getClassName().getId());
    }

    @Override
    public void deleteSection(Long id) {
        sectionRepository.deleteById(id);
    }
}
