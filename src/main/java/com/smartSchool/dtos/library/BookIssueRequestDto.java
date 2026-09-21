package com.smartSchool.dtos.library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookIssueRequestDto {
    private String libraryCardNo;
    private Long bookId;
    private LocalDate dueDate;
}

