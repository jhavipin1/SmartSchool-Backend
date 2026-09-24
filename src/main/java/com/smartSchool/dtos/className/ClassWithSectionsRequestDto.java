package com.smartSchool.dtos.className;

import com.smartSchool.dtos.section.SectionRequestDto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassWithSectionsRequestDto {

    @NotBlank(message = "Class name is required")
    private String className;

    private List<SectionRequestDto> sections;
}
