package com.smartSchool.services;

import com.smartSchool.dtos.homework.HomeworkResponseDto;
import com.smartSchool.dtos.homework.TeacherHomeworkRequestDto;
import com.smartSchool.dtos.teacher.TeacherRequestDto;
import com.smartSchool.dtos.teacher.TeacherResponseDto;

import java.util.List;

public interface TeacherService {
    TeacherResponseDto createTeacher(TeacherRequestDto dto);
    TeacherResponseDto getTeacherById(Long id);
    List<TeacherResponseDto> getAllTeachers();
    TeacherResponseDto getTeacherByStaffId(Long staffId);
    void deleteTeacher(Long id);

    // Homework features
    HomeworkResponseDto assignHomework(TeacherHomeworkRequestDto dto);
    List<HomeworkResponseDto> getHomeworksByTeacher(Long teacherId);
}