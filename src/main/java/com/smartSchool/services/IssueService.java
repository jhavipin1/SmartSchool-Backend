package com.smartSchool.services;

import com.smartSchool.dtos.library.BookIssueRequestDto;
import com.smartSchool.dtos.library.BookReturnRequestDto;
import com.smartSchool.dtos.library.IssueResponseDto;

public interface IssueService {
    IssueResponseDto issueBook(BookIssueRequestDto request);
    IssueResponseDto returnBook(BookReturnRequestDto request);
}
