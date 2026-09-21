package com.smartSchool.dtos.library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookReturnRequestDto {
    private Long issueRecordId;
}
