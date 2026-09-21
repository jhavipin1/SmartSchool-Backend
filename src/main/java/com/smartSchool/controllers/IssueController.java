package com.smartSchool.controllers;

import com.smartSchool.dtos.library.BookIssueRequestDto;
import com.smartSchool.dtos.library.BookReturnRequestDto;
import com.smartSchool.dtos.library.IssueResponseDto;
import com.smartSchool.services.IssueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @PostMapping("/issue")
    public ResponseEntity<IssueResponseDto> issueBook(@RequestBody @Valid BookIssueRequestDto request) {
        return ResponseEntity.ok(issueService.issueBook(request));
    }

    @PostMapping("/return")
    public ResponseEntity<IssueResponseDto> returnBook(@RequestBody @Valid BookReturnRequestDto request) {
        return ResponseEntity.ok(issueService.returnBook(request));
    }
}
