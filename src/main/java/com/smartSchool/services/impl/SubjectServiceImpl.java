package com.smartSchool.services.impl;

import com.smartSchool.dtos.subject.*;
import com.smartSchool.entities.Subject;

import com.smartSchool.exceptions.ResourceNotFoundException;
import com.smartSchool.repositories.SubjectRepository;
import com.smartSchool.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Override
    @Transactional
    public SubjectResponseDto createSubject(SubjectRequestDto dto) {
        Subject subject = Subject.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .description(dto.getDescription())
                .active(true)
                .build();
        return mapToResponse(subjectRepository.save(subject));
    }

    @Override
    @Transactional
    public SubjectResponseDto updateSubject(Long id, SubjectRequestDto dto) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + id));
        subject.setName(dto.getName());
        subject.setCode(dto.getCode());
        subject.setDescription(dto.getDescription());
        return mapToResponse(subjectRepository.save(subject));
    }

    @Override
    @Transactional
    public SubjectResponseDto updateSubjectStatus(Long id, SubjectStatusUpdateDto dto) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + id));
        subject.setActive(dto.getActive());
        return mapToResponse(subjectRepository.save(subject));
    }

    @Override
    public SubjectResponseDto getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + id));
    }

    @Override
    public List<SubjectResponseDto> getAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteSubject(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Subject not found with id: " + id);
        }
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