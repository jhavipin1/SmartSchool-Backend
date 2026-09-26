package com.smartSchool.services.impl;

import com.smartSchool.dtos.exam.*;
import com.smartSchool.entities.*;
import com.smartSchool.exceptions.ResourceNotFoundException;
import com.smartSchool.repositories.*;
import com.smartSchool.services.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExamServiceImpl implements ExamService {

    private final ExamGroupRepository examGroupRepo;
    private final ExamTypeRepository examTypeRepo;
    private final StudentExamRepository studentExamRepo;
    private final SubjectRepository subjectRepo;
    private final ClassNameRepository classRepo;
    private final StudentRepository studentRepo;
    private final SectionRepository sectionRepo;

    @Override
    @Transactional
    public ExamGroupDTO createExamGroup(ExamGroupDTO dto) {
        ExamGroup group = ExamGroup.builder().name(dto.getName()).build();
        group = examGroupRepo.save(group);
        return new ExamGroupDTO(group.getId(), group.getName());
    }

    @Override
    public List<ExamGroupDTO> getAllExamGroups() {
        return examGroupRepo.findAll().stream()
                .map(g -> new ExamGroupDTO(g.getId(), g.getName()))
                .toList();
    }

    @Override
    public ExamGroupDTO getExamGroupById(Long id) {
        ExamGroup group = examGroupRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ExamGroup not found with id: " + id));
        return new ExamGroupDTO(group.getId(), group.getName());
    }

    @Override
    @Transactional
    public void deleteExamGroup(Long id) {
        if (!examGroupRepo.existsById(id)) {
            throw new ResourceNotFoundException("ExamGroup not found with id: " + id);
        }
        examGroupRepo.deleteById(id);
    }

    @Override
    @Transactional
    public ExamTypeDTO createExamType(ExamTypeDTO dto) {
        ExamGroup group = examGroupRepo.findById(dto.getExamGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("ExamGroup not found with id: " + dto.getExamGroupId()));
        ExamType type = ExamType.builder().typeName(dto.getTypeName()).examGroup(group).build();
        type = examTypeRepo.save(type);
        return new ExamTypeDTO(type.getId(), type.getTypeName(), group.getId());
    }

    @Override
    public List<ExamTypeDTO> getExamTypesByGroupId(Long groupId) {
        return examTypeRepo.findByExamGroup_Id(groupId).stream()
                .map(t -> new ExamTypeDTO(t.getId(), t.getTypeName(), groupId))
                .toList();
    }

    @Override
    @Transactional
    public ExamSubjectDTO assignSubject(ExamSubjectDTO dto) {
        Subject subject = subjectRepo.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + dto.getSubjectId()));
        ClassName className = classRepo.findById(dto.getClassId())
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + dto.getClassId()));
        ExamType examType = examTypeRepo.findById(dto.getExamTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("ExamType not found with id: " + dto.getExamTypeId()));

        subject.setClassName(className);
        subject.setExamType(examType);
        subject.setMaxMarks(dto.getMaxMarks());

        subject = subjectRepo.save(subject);

        return new ExamSubjectDTO(
                subject.getId(),
                subject.getId(),
                className.getId(),
                examType.getId(),
                subject.getMaxMarks()
        );
    }

    @Override
    @Transactional
    public void deleteExamSubject(Long id) {
        if (!subjectRepo.existsById(id)) {
            throw new ResourceNotFoundException("Subject not found with id: " + id);
        }
        subjectRepo.deleteById(id);
    }

    @Override
    @Transactional
    public StudentExamDTO recordStudentMarks(StudentExamDTO dto) {
        Student student = studentRepo.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + dto.getStudentId()));
        Subject subject = subjectRepo.findById(dto.getExamSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + dto.getExamSubjectId()));

        StudentExam studentExam = studentExamRepo
                .findByStudent_IdAndSubject_Id(dto.getStudentId(), dto.getExamSubjectId())
                .map(existing -> {
                    existing.setMarksObtained(dto.getMarksObtained());
                    return existing;
                })
                .orElseGet(() -> StudentExam.builder()
                        .student(student)
                        .subject(subject)
                        .marksObtained(dto.getMarksObtained())
                        .build());

        studentExam = studentExamRepo.save(studentExam);

        return new StudentExamDTO(
                studentExam.getId(),
                student.getId(),
                subject.getId(),
                studentExam.getMarksObtained()
        );
    }

    @Override
    @Transactional
    public List<StudentExamDTO> recordBulkStudentMarks(List<StudentExamDTO> dtos) {
        return dtos.stream().map(this::recordStudentMarks).toList();
    }

    @Override
    public List<StudentExamDTO> getResultsByClassAndSubject(Long classId, Long subjectId) {
        List<StudentExam> studentExams = studentExamRepo
                .findBySubject_ClassName_IdAndSubject_Id(classId, subjectId);

        return studentExams.stream()
                .map(se -> new StudentExamDTO(
                        se.getId(),
                        se.getStudent().getId(),
                        se.getSubject().getId(),
                        se.getMarksObtained()
                ))
                .toList();
    }

    @Override
    public ExamTypeSummaryDTO getExamTypeSummary(Long examTypeId, Long classId, Long subjectId) {
        List<StudentExam> studentExams = studentExamRepo
                .findBySubject_ExamType_IdAndSubject_ClassName_IdAndSubject_Id(
                        examTypeId, classId, subjectId);

        if (studentExams.isEmpty()) {
            return new ExamTypeSummaryDTO(examTypeId, "N/A", classId, subjectId, 0.0, 0, 0);
        }

        Double avg = studentExams.stream().mapToInt(StudentExam::getMarksObtained).average().orElse(0.0);
        Integer max = studentExams.stream().mapToInt(StudentExam::getMarksObtained).max().orElse(0);
        Integer min = studentExams.stream().mapToInt(StudentExam::getMarksObtained).min().orElse(0);

        String examTypeName = studentExams.get(0).getSubject().getExamType().getTypeName();

        return new ExamTypeSummaryDTO(examTypeId, examTypeName, classId, subjectId, avg, max, min);
    }

    @Override
    @Transactional
    public ExamSubjectDTO createFullExamSetup(ExamSetupRequestDto dto) {
        ExamGroup examGroup = examGroupRepo.findByName(dto.getExamGroupName())
                .orElseGet(() -> examGroupRepo.save(ExamGroup.builder().name(dto.getExamGroupName()).build()));

        ExamType examType = examTypeRepo.findByTypeNameAndExamGroup_Id(dto.getExamTypeName(), examGroup.getId())
                .orElseGet(() -> examTypeRepo.save(ExamType.builder().typeName(dto.getExamTypeName()).examGroup(examGroup).build()));

        ClassName classEntity = classRepo.findByClassName(dto.getClassName())
                .orElseGet(() -> classRepo.save(ClassName.builder().className(dto.getClassName()).build()));

        Subject subject = subjectRepo.findByName(dto.getSubjectName())
                .orElseGet(() -> subjectRepo.save(
                        Subject.builder()
                                .name(dto.getSubjectName())
                                .active(true)
                                .build()
                ));

        subject.setClassName(classEntity);
        subject.setExamType(examType);
        subject.setMaxMarks(dto.getMaxMarks());

        subject = subjectRepo.save(subject);

        return new ExamSubjectDTO(
                subject.getId(),
                subject.getId(),
                classEntity.getId(),
                examType.getId(),
                subject.getMaxMarks()
        );
    }

    @Override
    @Transactional
    public List<ExamSubjectDTO> createBulkExamSetup(ExamSetupBulkRequestDto bulkDto) {
        return bulkDto.getSetups().stream()
                .map(this::createFullExamSetup)
                .toList();
    }
}