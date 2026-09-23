package com.smartSchool.services;

import com.smartSchool.dtos.exam.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExamService {
    ExamGroupDTO createExamGroup(ExamGroupDTO dto);
    ExamTypeDTO createExamType(ExamTypeDTO dto);
    ExamSubjectDTO assignSubject(ExamSubjectDTO dto);
    StudentExamDTO recordStudentMarks(StudentExamDTO dto);
    List<StudentExamDTO> getResultsByClassAndSubject(Long classId, Long subjectId);
    ExamTypeSummaryDTO getExamTypeSummary(Long examTypeId, Long classId, Long subjectId);


}