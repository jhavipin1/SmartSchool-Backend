package com.smartSchool.services;

import com.smartSchool.dtos.exam.*;
import java.util.List;

public interface ExamService {
    ExamGroupDTO createExamGroup(ExamGroupDTO dto);
    List<ExamGroupDTO> getAllExamGroups();
    ExamGroupDTO getExamGroupById(Long id);
    void deleteExamGroup(Long id);

    ExamTypeDTO createExamType(ExamTypeDTO dto);
    List<ExamTypeDTO> getExamTypesByGroupId(Long groupId);

    ExamSubjectDTO assignSubject(ExamSubjectDTO dto);
    void deleteExamSubject(Long id);

    StudentExamDTO recordStudentMarks(StudentExamDTO dto);
    List<StudentExamDTO> recordBulkStudentMarks(List<StudentExamDTO> dtos);

    List<StudentExamDTO> getResultsByClassSectionAndSubject(Long classId, Long sectionId, Long subjectId);
    ExamTypeSummaryDTO getExamTypeSummary(Long examTypeId, Long classId, Long sectionId, Long subjectId);

    ExamSubjectDTO createFullExamSetup(ExamSetupRequestDto dto);
    List<ExamSubjectDTO> createBulkExamSetup(ExamSetupBulkRequestDto bulkDto);
}