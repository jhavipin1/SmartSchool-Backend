package com.smartSchool.services.impl;

import com.smartSchool.dtos.student.StudentRequestDto;
import com.smartSchool.dtos.student.StudentResponseDto;
import com.smartSchool.entities.ClassName;
import com.smartSchool.entities.Parent;
import com.smartSchool.entities.Section;
import com.smartSchool.entities.Student;
import com.smartSchool.entities.User;
import com.smartSchool.enums.Gender;
import com.smartSchool.repositories.ClassNameRepository;
import com.smartSchool.repositories.ParentRepository;
import com.smartSchool.repositories.SectionRepository;
import com.smartSchool.repositories.StudentRepository;
import com.smartSchool.repositories.UserRepository;
import com.smartSchool.services.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ClassNameRepository classNameRepository;
    private final SectionRepository sectionRepository;
    private final UserRepository userRepository;
    private final ParentRepository parentRepository;

    public StudentServiceImpl(StudentRepository studentRepository,
                              ClassNameRepository classNameRepository,
                              SectionRepository sectionRepository,
                              UserRepository userRepository,
                              ParentRepository parentRepository) {
        this.studentRepository = studentRepository;
        this.classNameRepository = classNameRepository;
        this.sectionRepository = sectionRepository;
        this.userRepository = userRepository;
        this.parentRepository = parentRepository;
    }

    @Override
    public StudentResponseDto createStudent(StudentRequestDto dto) {
        validateUniqueFields(dto, null);

        Student student = mapToEntity(dto);
        Student savedStudent = studentRepository.save(student);

        return mapToDto(savedStudent);
    }

    @Override
    public List<StudentResponseDto> createStudentsBulk(List<StudentRequestDto> dtos) {
        List<Student> students = dtos.stream().map(dto -> {
            validateUniqueFields(dto, null);
            return mapToEntity(dto);
        }).collect(Collectors.toList());

        List<Student> savedStudents = studentRepository.saveAll(students);

        return savedStudents.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentResponseDto> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable).map(this::mapToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDto getStudentById(Long id) {
        Student student = findEntityById(id);
        return mapToDto(student);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDto getStudentByAdmissionNumber(String admissionNumber) {
        Student student = studentRepository.findByAdmissionNumber(admissionNumber)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with admission number: " + admissionNumber));
        return mapToDto(student);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDto getStudentByRollNumber(String rollNumber) {
        Student student = studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with roll number: " + rollNumber));
        return mapToDto(student);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentResponseDto> filterStudents(Long classId, Long sectionId, Gender gender, Pageable pageable) {
        return studentRepository.filterStudents(classId, sectionId, gender, pageable).map(this::mapToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentResponseDto> searchStudents(String query, Pageable pageable) {
        return studentRepository.searchStudents(query, pageable).map(this::mapToDto);
    }

    @Override
    public StudentResponseDto updateStudent(Long id, StudentRequestDto dto) {
        Student existingStudent = findEntityById(id);
        validateUniqueFields(dto, id);

        updateStudentFields(existingStudent, dto);

        Student updatedStudent = studentRepository.save(existingStudent);
        return mapToDto(updatedStudent);
    }

    @Override
    public StudentResponseDto reassignClassAndSection(Long id, Long newClassId, Long newSectionId) {
        Student student = findEntityById(id);

        ClassName className = classNameRepository.findById(newClassId)
                .orElseThrow(() -> new EntityNotFoundException("Class not found with ID: " + newClassId));
        Section section = sectionRepository.findById(newSectionId)
                .orElseThrow(() -> new EntityNotFoundException("Section not found with ID: " + newSectionId));

        student.setClassName(className);
        student.setSection(section);

        return mapToDto(studentRepository.save(student));
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = findEntityById(id);
        studentRepository.delete(student);
    }

    // --- Helper Methods ---

    private Student findEntityById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + id));
    }

    private void validateUniqueFields(StudentRequestDto dto, Long currentStudentId) {
        if (dto.getAdmissionNumber() != null) {
            studentRepository.findByAdmissionNumber(dto.getAdmissionNumber())
                    .ifPresent(s -> {
                        if (!s.getId().equals(currentStudentId)) {
                            throw new IllegalArgumentException("Admission number already exists: " + dto.getAdmissionNumber());
                        }
                    });
        }

        if (dto.getUserId() != null) {
            if (currentStudentId == null && studentRepository.existsByUserId(dto.getUserId())) {
                throw new IllegalArgumentException("User ID " + dto.getUserId() + " is already assigned to another student.");
            }
        }
    }

    private Student mapToEntity(StudentRequestDto dto) {
        User user = userRepository.findById(Math.toIntExact(dto.getUserId() != null ? dto.getUserId().longValue() : null))
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + dto.getUserId()));
        ClassName className = dto.getClassNameId() != null ?
                classNameRepository.findById(dto.getClassNameId().longValue()).orElse(null) : null;

        Section section = dto.getSectionId() != null ?
                sectionRepository.findById(dto.getSectionId().longValue()).orElse(null) : null;

        Parent parent = dto.getParentId() != null ?
                parentRepository.findById(dto.getParentId().longValue()).orElse(null) : null;

        Student student = Student.builder()
                .admissionNumber(dto.getAdmissionNumber())
                .rollNumber(dto.getRollNumber())
                .firstName(dto.getFirstName())
                .middleName(dto.getMiddleName())
                .lastName(dto.getLastName())
                .dateOfBirth(dto.getDateOfBirth())
                .gender(dto.getGender())
                .category(dto.getCategory())
                .religion(dto.getReligion())
                .bloodGroup(dto.getBloodGroup())
                .house(dto.getHouse())
                .className(className)
                .section(section)
                .mobileNo(dto.getMobileNo())
                .email(dto.getEmail())
                .admissionDate(dto.getAdmissionDate())
                .height(dto.getHeight())
                .weight(dto.getWeight())
                .measurementDate(dto.getMeasurementDate())
                .fatherName(dto.getFatherName())
                .fatherPhone(dto.getFatherPhone())
                .fatherOcc(dto.getFatherOcc())
                .motherName(dto.getMotherName())
                .motherPhone(dto.getMotherPhone())
                .motherOcc(dto.getMotherOcc())
                .guardianIs(dto.getGuardianIs())
                .guardianName(dto.getGuardianName())
                .guardianRelation(dto.getGuardianRelation())
                .guardianEmail(dto.getGuardianEmail())
                .guardianPhone(dto.getGuardianPhone())
                .guardianOcc(dto.getGuardianOcc())
                .guardianAddress(dto.getGuardianAddress())
                .currentAddress(dto.getCurrentAddress())
                .permanentAddress(dto.getPermanentAddress())
                .bankAccountNo(dto.getBankAccountNo())
                .bankName(dto.getBankName())
                .ifscCode(dto.getIfscCode())
                .nationalIdentificationNo(dto.getNationalIdentificationNo())
                .localIdentificationNo(dto.getLocalIdentificationNo())
                .rte(dto.getRte())
                .previousSchool(dto.getPreviousSchool())
                .note(dto.getNote())
                .user(user)
                .parent(parent)
                .build();

        return student;
    }

    private void updateStudentFields(Student student, StudentRequestDto dto) {
        student.setAdmissionNumber(dto.getAdmissionNumber());
        student.setRollNumber(dto.getRollNumber());
        student.setFirstName(dto.getFirstName());
        student.setMiddleName(dto.getMiddleName());
        student.setLastName(dto.getLastName());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setGender(dto.getGender());
        student.setCategory(dto.getCategory());
        student.setReligion(dto.getReligion());
        student.setBloodGroup(dto.getBloodGroup());
        student.setHouse(dto.getHouse());
        student.setMobileNo(dto.getMobileNo());
        student.setEmail(dto.getEmail());
        student.setAdmissionDate(dto.getAdmissionDate());
        student.setHeight(dto.getHeight());
        student.setWeight(dto.getWeight());
        student.setMeasurementDate(dto.getMeasurementDate());
        student.setFatherName(dto.getFatherName());
        student.setFatherPhone(dto.getFatherPhone());
        student.setFatherOcc(dto.getFatherOcc());
        student.setMotherName(dto.getMotherName());
        student.setMotherPhone(dto.getMotherPhone());
        student.setMotherOcc(dto.getMotherOcc());
        student.setGuardianIs(dto.getGuardianIs());
        student.setGuardianName(dto.getGuardianName());
        student.setGuardianRelation(dto.getGuardianRelation());
        student.setGuardianEmail(dto.getGuardianEmail());
        student.setGuardianPhone(dto.getGuardianPhone());
        student.setGuardianOcc(dto.getGuardianOcc());
        student.setGuardianAddress(dto.getGuardianAddress());
        student.setCurrentAddress(dto.getCurrentAddress());
        student.setPermanentAddress(dto.getPermanentAddress());
        student.setBankAccountNo(dto.getBankAccountNo());
        student.setBankName(dto.getBankName());
        student.setIfscCode(dto.getIfscCode());
        student.setNationalIdentificationNo(dto.getNationalIdentificationNo());
        student.setLocalIdentificationNo(dto.getLocalIdentificationNo());
        student.setRte(dto.getRte());
        student.setPreviousSchool(dto.getPreviousSchool());
        student.setNote(dto.getNote());

        if (dto.getClassNameId() != null) {
            student.setClassName(classNameRepository.findById(dto.getClassNameId()).orElse(null));
        }
        if (dto.getSectionId() != null) {
            student.setSection(sectionRepository.findById(dto.getSectionId()).orElse(null));
        }
        if (dto.getParentId() != null) {
            student.setParent(parentRepository.findById(dto.getParentId()).orElse(null));
        }
    }

    private StudentResponseDto mapToDto(Student student) {
        String fullName = String.format("%s %s %s",
                student.getFirstName(),
                student.getMiddleName() != null ? student.getMiddleName() : "",
                student.getLastName()).replaceAll("\\s+", " ").trim();

        return StudentResponseDto.builder()
                .id(student.getId())
                .admissionNumber(student.getAdmissionNumber())
                .rollNumber(student.getRollNumber())
                .firstName(student.getFirstName())
                .middleName(student.getMiddleName())
                .lastName(student.getLastName())
                .fullName(fullName)
                .dateOfBirth(student.getDateOfBirth())
                .gender(student.getGender())
                .category(student.getCategory())
                .religion(student.getReligion())
                .bloodGroup(student.getBloodGroup())
                .house(student.getHouse())
                .classNameId(student.getClassName() != null ? student.getClassName().getId() : null)
                .sectionId(student.getSection() != null ? student.getSection().getId() : null)
                .userId(student.getUser() != null ? student.getUser().getId() : null)
                .parentId(student.getParent() != null ? student.getParent().getId() : null)
                .mobileNo(student.getMobileNo())
                .email(student.getEmail())
                .admissionDate(student.getAdmissionDate())
                .height(student.getHeight())
                .weight(student.getWeight())
                .measurementDate(student.getMeasurementDate())
                .fatherName(student.getFatherName())
                .fatherPhone(student.getFatherPhone())
                .fatherOcc(student.getFatherOcc())
                .motherName(student.getMotherName())
                .motherPhone(student.getMotherPhone())
                .motherOcc(student.getMotherOcc())
                .guardianIs(student.getGuardianIs())
                .guardianName(student.getGuardianName())
                .guardianRelation(student.getGuardianRelation())
                .guardianEmail(student.getGuardianEmail())
                .guardianPhone(student.getGuardianPhone())
                .guardianOcc(student.getGuardianOcc())
                .guardianAddress(student.getGuardianAddress())
                .currentAddress(student.getCurrentAddress())
                .permanentAddress(student.getPermanentAddress())
                .bankAccountNo(student.getBankAccountNo())
                .bankName(student.getBankName())
                .ifscCode(student.getIfscCode())
                .nationalIdentificationNo(student.getNationalIdentificationNo())
                .localIdentificationNo(student.getLocalIdentificationNo())
                .rte(student.getRte())
                .previousSchool(student.getPreviousSchool())
                .note(student.getNote())
                .build();
    }
}