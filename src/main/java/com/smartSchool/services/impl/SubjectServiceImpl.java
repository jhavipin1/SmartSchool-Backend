package com.smartSchool.services.impl;


import com.smartSchool.dtos.subject.*;
import com.smartSchool.entities.Subject;
import com.smartSchool.repositories.SubjectRepository;
import com.smartSchool.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Override
    public SubjectResponseDto createSubject(SubjectRequestDto dto) {
        Subject subject = Subject.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .description(dto.getDescription())
                .active(true)
                .build();
        subjectRepository.save(subject);
        return mapToResponse(subject);
    }

    @Override
    public SubjectResponseDto updateSubject(Long id, SubjectRequestDto dto) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        subject.setName(dto.getName());
        subject.setCode(dto.getCode());
        subject.setDescription(dto.getDescription());
        subjectRepository.save(subject);
        return mapToResponse(subject);
    }

    @Override
    public SubjectResponseDto updateSubjectStatus(Long id, SubjectStatusUpdateDto dto) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        subject.setActive(dto.getActive());
        subjectRepository.save(subject);
        return mapToResponse(subject);
    }

    @Override
    public SubjectResponseDto getSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        return mapToResponse(subject);
    }

    @Override
    public List<SubjectResponseDto> getAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }

    private SubjectResponseDto mapToResponse(Subject subject) {
        return SubjectResponseDto.builder()
                .id(subject.getId())
                .name(subject.getName())
                .code(subject.getCode())
                .description(subject.getDescription())
                .active(subject.getActive())
                .build();
    }
}
