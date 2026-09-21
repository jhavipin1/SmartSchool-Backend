package com.smartSchool.dtos.library;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RackDto {
    private Long id;
    private String rackCode;
    private String location;
}

