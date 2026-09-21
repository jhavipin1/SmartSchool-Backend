package com.smartSchool.dtos.subject;


import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SubjectResponseDto {
    private Long id;
    private String name;
    private String code;
    private String description;
    private Boolean active;
}

