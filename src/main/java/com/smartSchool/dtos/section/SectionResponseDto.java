package com.smartSchool.dtos.section;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionResponseDto {
    private Long id;
    private String sectionName;
    private Long classNameId;
}