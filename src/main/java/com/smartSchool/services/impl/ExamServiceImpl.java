package com.smartSchool.services.impl;

import com.smartSchool.dtos.exam.*;
import com.smartSchool.entities.*;
import com.smartSchool.repositories.*;
import com.smartSchool.services.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamGroupRepository examGroupRepo;
    private final ExamTypeRepository examTypeRepo;
    private final ExamSubjectRepository examSubjectRepo;
    private final StudentExamRepository studentExamRepo;
    private final SubjectRepository subjectRepo;
    private final ClassNameRepository classRepo;
    private final StudentRepository studentRepo;

    @Override
    public ExamGroupDTO createExamGroup(ExamGroupDTO dto) {
        ExamGroup group = ExamGroup.builder().name(dto.getName()).build();
        group = examGroupRepo.save(group);
        return new ExamGroupDTO(group.getId(), group.getName());
    }

    @Override
    public ExamTypeDTO createExamType(ExamTypeDTO dto) {
        ExamGroup group = examGroupRepo.findById(dto.getExamGroupId())
                .orElseThrow(() -> new RuntimeException("ExamGroup not found"));
        ExamType type = ExamType.builder().typeName(dto.getTypeName()).examGroup(group).build();
        type = examTypeRepo.save(type);
        return new ExamTypeDTO(type.getId(), type.getTypeName(), group.getId());
    }

    @Override
    public ExamSubjectDTO assignSubject(ExamSubjectDTO dto) {
        Subject subject = subjectRepo.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        ClassName className = classRepo.findById(dto.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));
        ExamSubject examSubject = ExamSubject.builder()
                .subject(subject).className(className).maxMarks(dto.getMaxMarks()).build();
        examSubject = examSubjectRepo.save(examSubject);
        return new ExamSubjectDTO(examSubject.getId(), subject.getId(), className.getId(), examSubject.getMaxMarks());
    }

    @Override
    public StudentExamDTO recordStudentMarks(StudentExamDTO dto) {
        Student student = studentRepo.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        ExamSubject examSubject = examSubjectRepo.findById(dto.getExamSubjectId())
                .orElseThrow(() -> new RuntimeException("ExamSubject not found"));
        StudentExam studentExam = StudentExam.builder()
                .student(student).examSubject(examSubject).marksObtained(dto.getMarksObtained()).build();
        studentExam = studentExamRepo.save(studentExam);
        return new StudentExamDTO(studentExam.getId(), student.getId(), examSubject.getId(), studentExam.getMarksObtained());
    }

    @Override
    public List<StudentExamDTO> getResultsByClassAndSubject(Long classId, Long subjectId) {
        List<StudentExam> studentExams = studentExamRepo.findByExamSubject_ClassName_IdAndExamSubject_Subject_Id(classId, subjectId);

        return studentExams.stream()
                .map(se -> new StudentExamDTO(
                        se.getId(),
                        se.getStudent().getId(),
                        se.getExamSubject().getId(),
                        se.getMarksObtained()
                ))
                .toList();
    }

    @Override
    public ExamTypeSummaryDTO getExamTypeSummary(Long examTypeId, Long classId, Long subjectId) {
        List<StudentExam> studentExams = studentExamRepo
                .findByExamSubject_ExamType_IdAndExamSubject_ClassName_IdAndExamSubject_Subject_Id(
                        examTypeId, classId, subjectId);

        if (studentExams.isEmpty()) {
            return new ExamTypeSummaryDTO(examTypeId, "N/A", classId, subjectId, 0.0, 0, 0);
        }

        Double avg = studentExams.stream()
                .mapToInt(StudentExam::getMarksObtained)
                .average()
                .orElse(0.0);

        Integer max = studentExams.stream()
                .mapToInt(StudentExam::getMarksObtained)
                .max()
                .orElse(0);

        Integer min = studentExams.stream()
                .mapToInt(StudentExam::getMarksObtained)
                .min()
                .orElse(0);

        String examTypeName = studentExams.get(0).getExamSubject().getExamType().getTypeName();

        return new ExamTypeSummaryDTO(examTypeId, examTypeName, classId, subjectId, avg, max, min);
    }


}
