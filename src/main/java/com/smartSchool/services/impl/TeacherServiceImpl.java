package com.smartSchool.services.impl;

import com.smartSchool.dtos.homework.HomeworkResponseDto;
import com.smartSchool.dtos.homework.TeacherHomeworkRequestDto;
import com.smartSchool.dtos.staff.StaffResponseDto;
import com.smartSchool.dtos.teacher.TeacherRequestDto;
import com.smartSchool.dtos.teacher.TeacherResponseDto;
import com.smartSchool.entities.*;
import com.smartSchool.enums.RoleName;
import com.smartSchool.repositories.*;
import com.smartSchool.services.TeacherService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final StaffRepository staffRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final HomeworkRepository homeworkRepository;
    private final ClassNameRepository classNameRepository;
    private final SectionRepository sectionRepository;

    @Override
    @Transactional
    public TeacherResponseDto createTeacher(TeacherRequestDto dto) {
        // ✅ Fetch Staff
        Staff staff = staffRepository.findById(dto.getStaffId())
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        User user = staff.getUser();
        if (user == null || user.getRole() == null || user.getRole().getName() != RoleName.TEACHER) {
            throw new IllegalArgumentException("Staff must be linked to a User with TEACHER role.");
        }

        List<Subject> subjects = (dto.getSubjectIds() != null && !dto.getSubjectIds().isEmpty())
                ? subjectRepository.findAllById(dto.getSubjectIds())
                : Collections.emptyList();

        Teacher teacher = Teacher.builder()
                .staff(staff)
                .subjects(subjects)
                .build();

        return mapToTeacherResponse(teacherRepository.save(teacher));
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherResponseDto getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
        return mapToTeacherResponse(teacher);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeacherResponseDto> getAllTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(this::mapToTeacherResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherResponseDto getTeacherByStaffId(Long staffId) {
        Teacher teacher = teacherRepository.findByStaffId(staffId)
                .orElseThrow(() -> new RuntimeException("Teacher not found for staff id: " + staffId));
        return mapToTeacherResponse(teacher);
    }

    @Override
    @Transactional
    public void deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new RuntimeException("Teacher not found with id: " + id);
        }
        teacherRepository.deleteById(id);
    }

    @Override
    @Transactional
    public HomeworkResponseDto assignHomework(TeacherHomeworkRequestDto dto) {
        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        ClassName className = classNameRepository.findById(dto.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        Section section = sectionRepository.findById(dto.getSectionId())
                .orElseThrow(() -> new RuntimeException("Section not found"));

        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        String createdBy = teacher.getStaff().getFirstName() + " " + teacher.getStaff().getLastName();

        Homework homework = Homework.builder()
                .className(className)
                .section(section)
                .subject(subject)
                .homeworkDate(dto.getHomeworkDate())
                .submissionDate(dto.getSubmissionDate())
                .evaluationDate(dto.getEvaluationDate())
                .maxMarks(dto.getMaxMarks())
                .description(dto.getDescription())
                .documentPath(dto.getDocumentPath())
                .createdBy(createdBy)
                .active(true)
                .build();

        Homework savedHomework = homeworkRepository.save(homework);
        return mapToHomeworkResponse(savedHomework);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HomeworkResponseDto> getHomeworksByTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        String authorName = teacher.getStaff().getFirstName() + " " + teacher.getStaff().getLastName();

        return homeworkRepository.findAll().stream()
                .filter(hw -> authorName.equalsIgnoreCase(hw.getCreatedBy()))
                .map(this::mapToHomeworkResponse)
                .collect(Collectors.toList());
    }

    private TeacherResponseDto mapToTeacherResponse(Teacher teacher) {
        Staff staff = teacher.getStaff();
        StaffResponseDto staffDto = StaffResponseDto.builder()
                .id(staff.getId())
                .employeeId(staff.getEmployeeId())
                .firstName(staff.getFirstName())
                .lastName(staff.getLastName())
                .phone(staff.getPhone())
                .joiningDate(staff.getJoiningDate())
                .departmentName(staff.getDepartment() != null ? staff.getDepartment().getDepartmentName() : null)
                .designationName(staff.getDesignation() != null ? staff.getDesignation().getDesignationName() : null)
                .username(staff.getUser() != null ? staff.getUser().getUsername() : null)
                .active(staff.getUser() != null && staff.getUser().isActive())
                .build();

        List<String> subjectNames = (teacher.getSubjects() != null)
                ? teacher.getSubjects().stream().map(Subject::getName).collect(Collectors.toList())
                : Collections.emptyList();

        return TeacherResponseDto.builder()
                .id(teacher.getId())
                .staffDetails(staffDto)
                .assignedSubjects(subjectNames)
                .build();
    }

    private HomeworkResponseDto mapToHomeworkResponse(Homework homework) {
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