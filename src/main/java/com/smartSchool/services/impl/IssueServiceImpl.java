package com.smartSchool.services.impl;

import com.smartSchool.dtos.library.BookIssueRequestDto;
import com.smartSchool.dtos.library.BookReturnRequestDto;
import com.smartSchool.dtos.library.IssueResponseDto;
import com.smartSchool.entities.Book;
import com.smartSchool.entities.IssueRecord;
import com.smartSchool.entities.Student;
import com.smartSchool.enums.IssueStatus;
import com.smartSchool.enums.LibraryCardStatus;
import com.smartSchool.repositories.BookRepository;
import com.smartSchool.repositories.IssueRecordRepository;
import com.smartSchool.repositories.StudentRepository;
import com.smartSchool.services.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Added import

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class IssueServiceImpl implements IssueService {

    private final StudentRepository studentRepo;
    private final BookRepository bookRepo;
    private final IssueRecordRepository issueRepo;

    @Override
    public IssueResponseDto issueBook(BookIssueRequestDto request) {
        Student student = studentRepo.findByLibraryCardNoAndLibraryCardStatus(
                        request.getLibraryCardNo(), LibraryCardStatus.ACTIVE)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or inactive library card"));

        Book book = bookRepo.findById(request.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (book.getAvailableQty() <= 0) {
            throw new IllegalStateException("Book is not available for issue");
        }

        book.setAvailableQty(book.getAvailableQty() - 1);
        bookRepo.save(book);

        IssueRecord record = IssueRecord.builder()
                .libraryCardNo(request.getLibraryCardNo())
                .student(student)
                .book(book)
                .issueDate(LocalDate.now())
                .dueDate(request.getDueDate())
                .status(IssueStatus.ISSUED)
                .build();

        IssueRecord saved = issueRepo.save(record);

        return toDto(saved);
    }

    @Override
    public IssueResponseDto returnBook(BookReturnRequestDto request) {
        IssueRecord record = issueRepo.findById(request.getIssueRecordId())
                .orElseThrow(() -> new IllegalArgumentException("Issue record not found"));

        if (record.getStatus() == IssueStatus.RETURNED) {
            throw new IllegalStateException("Book already returned");
        }

        Book book = record.getBook();
        book.setAvailableQty(book.getAvailableQty() + 1);
        bookRepo.save(book);

        record.setReturnDate(LocalDate.now());
        record.setStatus(IssueStatus.RETURNED);

        IssueRecord saved = issueRepo.save(record);

        return toDto(saved);
    }

    private IssueResponseDto toDto(IssueRecord record) {
        return IssueResponseDto.builder()
                .issueId(record.getId())
                .bookTitle(record.getBook().getTitle())
                .libraryCardNo(record.getLibraryCardNo())
                .issueDate(record.getIssueDate())
                .dueDate(record.getDueDate())
                .returnDate(record.getReturnDate())
                .status(record.getStatus().name())
                .build();
    }
}