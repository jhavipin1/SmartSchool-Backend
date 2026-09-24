package com.smartSchool.dtos.className;

import com.smartSchool.dtos.section.SectionResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassNameResponseDto {
    private Long id;
    private String className;
    private List<SectionResponseDto> sections;
}