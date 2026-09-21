package com.smartSchool.services;

import com.smartSchool.dtos.homework.*;

import java.util.List;

public interface HomeworkService {
    HomeworkResponseDto createHomework(HomeworkRequestDto dto);
    HomeworkResponseDto updateHomework(Long id, HomeworkRequestDto dto);
    HomeworkResponseDto updateHomeworkStatus(Long id, HomeworkStatusUpdateDto dto);
    HomeworkResponseDto getHomeworkById(Long id);
    List<HomeworkResponseDto> getAllHomeworks();
    void deleteHomework(Long id);
}
