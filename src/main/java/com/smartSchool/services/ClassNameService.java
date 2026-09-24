package com.smartSchool.services;

import com.smartSchool.dtos.className.ClassNameRequestDto;
import com.smartSchool.dtos.className.ClassNameResponseDto;
import com.smartSchool.dtos.className.ClassWithSectionsRequestDto;

import java.util.List;

public interface ClassNameService {
    ClassNameResponseDto createClass(ClassNameRequestDto dto);
    List<ClassNameResponseDto> createClassesBulk(List<ClassNameRequestDto> dtos);
    List<ClassNameResponseDto> getAllClasses();
    ClassNameResponseDto getClassById(Long id);
    ClassNameResponseDto updateClass(Long id, ClassNameRequestDto dto);
    void deleteClass(Long id);
    ClassNameResponseDto createClassWithSections(ClassWithSectionsRequestDto dto);

}
