package com.smartSchool.services;

import com.smartSchool.dtos.library.LibraryCardUpdateRequestDto;
import com.smartSchool.dtos.library.StudentSearchRequestDto;
import com.smartSchool.dtos.student.StudentRequestDto;
import com.smartSchool.dtos.student.StudentResponseDto;
import com.smartSchool.enums.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {

    StudentResponseDto createStudent(StudentRequestDto dto);

    List<StudentResponseDto> createStudentsBulk(List<StudentRequestDto> dtos);

    Page<StudentResponseDto> getAllStudents(Pageable pageable);

    StudentResponseDto getStudentById(Long id);

    StudentResponseDto getStudentByAdmissionNumber(String admissionNumber);

    StudentResponseDto getStudentByRollNumber(String rollNumber);

    Page<StudentResponseDto> filterStudents(Long classId, Long sectionId, Gender gender, Pageable pageable);

    Page<StudentResponseDto> searchStudents(String query, Pageable pageable);

    StudentResponseDto updateStudent(Long id, StudentRequestDto dto);

    StudentResponseDto reassignClassAndSection(Long id, Long newClassId, Long newSectionId);

    void deleteStudent(Long id);
     // Library
    List<StudentResponseDto> searchStudents(StudentSearchRequestDto dto);
    StudentResponseDto assignLibraryCard(Long studentId, LibraryCardUpdateRequestDto dto);
}