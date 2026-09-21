package com.smartSchool.services.impl;


import com.smartSchool.dtos.homework.*;
import com.smartSchool.entities.*;
import com.smartSchool.repositories.*;
import com.smartSchool.services.HomeworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeworkServiceImpl implements HomeworkService {

    private final HomeworkRepository homeworkRepository;
    private final ClassNameRepository classNameRepository;
    private final SectionRepository sectionRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public HomeworkResponseDto createHomework(HomeworkRequestDto dto) {
        Homework homework = Homework.builder()
                .className(classNameRepository.findById(dto.getClassId())
                        .orElseThrow(() -> new RuntimeException("Class not found")))
                .section(sectionRepository.findById(dto.getSectionId())
                        .orElseThrow(() -> new RuntimeException("Section not found")))
                .subject(subjectRepository.findById(dto.getSubjectId())
                        .orElseThrow(() -> new RuntimeException("Subject not found")))
                .homeworkDate(dto.getHomeworkDate())
                .submissionDate(dto.getSubmissionDate())
                .evaluationDate(dto.getEvaluationDate())
                .maxMarks(dto.getMaxMarks())
                .description(dto.getDescription())
                .documentPath(dto.getDocumentPath())
                .createdBy(dto.getCreatedBy())
                .active(true)
                .build();
        homeworkRepository.save(homework);
        return mapToResponse(homework);
    }

    @Override
    public HomeworkResponseDto updateHomework(Long id, HomeworkRequestDto dto) {
        Homework homework = homeworkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Homework not found"));
        homework.setClassName(classNameRepository.findById(dto.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found")));
        homework.setSection(sectionRepository.findById(dto.getSectionId())
                .orElseThrow(() -> new RuntimeException("Section not found")));
        homework.setSubject(subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found")));
        homework.setHomeworkDate(dto.getHomeworkDate());
        homework.setSubmissionDate(dto.getSubmissionDate());
        homework.setEvaluationDate(dto.getEvaluationDate());
        homework.setMaxMarks(dto.getMaxMarks());
        homework.setDescription(dto.getDescription());
        homework.setDocumentPath(dto.getDocumentPath());
        homework.setCreatedBy(dto.getCreatedBy());
        homeworkRepository.save(homework);
        return mapToResponse(homework);
    }

    @Override
    public HomeworkResponseDto updateHomeworkStatus(Long id, HomeworkStatusUpdateDto dto) {
        Homework homework = homeworkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Homework not found"));
        homework.setActive(dto.getActive());
        homeworkRepository.save(homework);
        return mapToResponse(homework);
    }

    @Override
    public HomeworkResponseDto getHomeworkById(Long id) {
        Homework homework = homeworkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Homework not found"));
        return mapToResponse(homework);
    }

    @Override
    public List<HomeworkResponseDto> getAllHomeworks() {
        return homeworkRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteHomework(Long id) {
        homeworkRepository.deleteById(id);
    }

    private HomeworkResponseDto mapToResponse(Homework homework) {
        return HomeworkResponseDto.builder()
                .id(homework.getId())
                .className(homework.getClassName().getClassName())
                .sectionName(homework.getSection().getSectionName())
                .subjectName(homework.getSubject().getName())
                .homeworkDate(homework.getHomeworkDate())
                .submissionDate(homework.getSubmissionDate())
                .evaluationDate(homework.getEvaluationDate())
                .maxMarks(homework.getMaxMarks())
                .description(homework.getDescription())
                .documentPath(homework.getDocumentPath())
                .createdBy(homework.getCreatedBy())
                .active(homework.getActive())
                .build();
    }
}
