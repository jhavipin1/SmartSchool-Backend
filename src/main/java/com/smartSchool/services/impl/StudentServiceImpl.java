package com.smartSchool.services.impl;

import com.smartSchool.dtos.library.LibraryCardUpdateRequestDto;
import com.smartSchool.dtos.library.StudentSearchRequestDto;
import com.smartSchool.dtos.student.StudentRequestDto;
import com.smartSchool.dtos.student.StudentResponseDto;
import com.smartSchool.entities.*;
import com.smartSchool.enums.FeePaymentStatus;
import com.smartSchool.enums.Gender;
import com.smartSchool.enums.RoleName;
import com.smartSchool.repositories.*;
import com.smartSchool.services.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private final LibraryCardAuditRepository auditRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentServiceImpl(StudentRepository studentRepository,
                              ClassNameRepository classNameRepository,
                              SectionRepository sectionRepository,
                              UserRepository userRepository,
                              ParentRepository parentRepository,
                              LibraryCardAuditRepository auditRepository,
                              RoleRepository roleRepository,
                              PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.classNameRepository = classNameRepository;
        this.sectionRepository = sectionRepository;
        this.userRepository = userRepository;
        this.parentRepository = parentRepository;
        this.auditRepository = auditRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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

    }

    private Student mapToEntity(StudentRequestDto dto) {
        String username = dto.getUsername() != null ? dto.getUsername() : dto.getAdmissionNumber();
        String rawPassword = dto.getPassword() != null
                ? dto.getPassword()
                : dto.getDateOfBirth().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String email = dto.getEmail() != null ? dto.getEmail() : dto.getAdmissionNumber() + "@smartschool.com";
        Role studentRole = roleRepository.findByName(RoleName.STUDENT)
                .orElseThrow(() -> new EntityNotFoundException("Role STUDENT not found"));
        User user = User.builder()
                .fullName(dto.getFirstName() + " " + dto.getLastName())
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .email(email)
                .role(studentRole)
                .active(true)
                .build();

        userRepository.save(user);

        ClassName className = null;
        if (dto.getClassNameId() != null) {
            className = classNameRepository.findById(dto.getClassNameId())
                    .orElseThrow(() -> new EntityNotFoundException("Class not found with ID: " + dto.getClassNameId()));
        }

        Section section = null;
        if (dto.getSectionId() != null) {
            section = sectionRepository.findById(dto.getSectionId())
                    .orElseThrow(() -> new EntityNotFoundException("Section not found with ID: " + dto.getSectionId()));
            if (className != null && !section.getClassName().getId().equals(className.getId())) {
                throw new IllegalArgumentException("Section " + section.getId() + " does not belong to Class " + className.getId());
            }
        }

        Parent parent = null;
        if (dto.getFatherName() != null) {
            parent = Parent.builder()
                    .firstName(dto.getFatherName())
                    .lastName(dto.getLastName()) // or father’s surname
                    .phone(dto.getFatherPhone())
                    .occupation(dto.getFatherOcc())
                    .address(dto.getCurrentAddress())
                    .user(user)
                    .build();
        } else if (dto.getMotherName() != null) {
            parent = Parent.builder()
                    .firstName(dto.getMotherName())
                    .lastName(dto.getLastName())
                    .phone(dto.getMotherPhone())
                    .occupation(dto.getMotherOcc())
                    .address(dto.getCurrentAddress())
                    .user(user)
                    .build();
        } else if (dto.getGuardianName() != null) {
            parent = Parent.builder()
                    .firstName(dto.getGuardianName())
                    .lastName(dto.getLastName())
                    .phone(dto.getGuardianPhone())
                    .occupation(dto.getGuardianOcc())
                    .address(dto.getGuardianAddress())
                    .user(user)
                    .build();
        }

        if (parent != null) {
            parentRepository.save(parent);
        }



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


    @Override
    public List<StudentResponseDto> searchStudents(StudentSearchRequestDto dto) {
        if (dto.getAdmissionNumber() != null) {
            return studentRepository.findByAdmissionNumber(dto.getAdmissionNumber())
                    .map(this::mapToDto).stream().toList();
        }
        if (dto.getRollNumber() != null) {
            return studentRepository.findByRollNumber(dto.getRollNumber())
                    .map(this::mapToDto).stream().toList();
        }
        if (dto.getName() != null) {
            return studentRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(dto.getName(), dto.getName())
                    .stream().map(this::mapToDto).toList();
        }
        return List.of();
    }

    @Override
    public StudentResponseDto assignLibraryCard(Long studentId, LibraryCardUpdateRequestDto dto) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setLibraryCardNo(dto.getLibraryCardNo());
        student.setLibraryCardStatus(dto.getStatus());
        studentRepository.save(student);

        // --- Audit log ---
        LibraryCardAudit audit = LibraryCardAudit.builder()
                .studentId(student.getId())
                .studentName(student.getFirstName() + " " + student.getLastName())
                .libraryCardNo(dto.getLibraryCardNo())
                .status(dto.getStatus())
                .librarianId(getCurrentLibrarianId()) // from security context
                .librarianName(getCurrentLibrarianName())
                .actionTime(LocalDateTime.now())
                .action("ASSIGN_CARD")
                .build();
        auditRepository.save(audit);

        return mapToDto(student);
    }

    // Example helper methods
    private Long getCurrentLibrarianId() {
        // fetch from JWT / SecurityContext
        return 101L;
    }

    private String getCurrentLibrarianName() {
        return "Admin Librarian";
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
            ClassName className = classNameRepository.findById(dto.getClassNameId())
                    .orElseThrow(() -> new EntityNotFoundException("Class not found with ID: " + dto.getClassNameId()));
            student.setClassName(className);
        }

        if (dto.getSectionId() != null) {
            Section section = sectionRepository.findById(dto.getSectionId())
                    .orElseThrow(() -> new EntityNotFoundException("Section not found with ID: " + dto.getSectionId()));
            if (student.getClassName() != null && !section.getClassName().getId().equals(student.getClassName().getId())) {
                throw new IllegalArgumentException("Section " + section.getId() + " does not belong to Class " + student.getClassName().getId());
            }
            student.setSection(section);
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
                .libraryCardNo(student.getLibraryCardNo())
                .libraryCardStatus(student.getLibraryCardStatus())
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
                .className(student.getClassName() != null ? student.getClassName().getClassName() : null)
                .sectionId(student.getSection() != null ? student.getSection().getId() : null)
                .sectionName(student.getSection() != null ? student.getSection().getSectionName() : null)
                .userId(student.getUser() != null ? student.getUser().getId() : null)
                .userUsername(student.getUser() != null ? student.getUser().getUsername() : null)
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