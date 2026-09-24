package com.smartSchool.services;

import com.smartSchool.dtos.section.SectionRequestDto;
import com.smartSchool.dtos.section.SectionResponseDto;

import java.util.List;

public interface SectionService {
    SectionResponseDto createSection(SectionRequestDto dto);
    List<SectionResponseDto> createSectionsBulk(List<SectionRequestDto> dtos);
    List<SectionResponseDto> getSectionsByClass(Long classId);
    SectionResponseDto updateSection(Long id, SectionRequestDto dto);
    void deleteSection(Long id);
}
