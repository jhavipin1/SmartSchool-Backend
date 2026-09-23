package com.smartSchool.dtos.exam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamTypeDTO {
    private Long id;
    private String typeName;
    private Long examGroupId;
}