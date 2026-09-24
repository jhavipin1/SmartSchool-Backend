package com.smartSchool.services;

import com.smartSchool.dtos.subject.*;
import java.util.List;

public interface SubjectService {
    SubjectResponseDto createSubject(SubjectRequestDto dto);
    SubjectResponseDto updateSubject(Long id, SubjectRequestDto dto);
    SubjectResponseDto updateSubjectStatus(Long id, SubjectStatusUpdateDto dto);
    SubjectResponseDto getSubjectById(Long id);
    List<SubjectResponseDto> getAllSubjects();
    void deleteSubject(Long id);
}