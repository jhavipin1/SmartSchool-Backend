package com.smartSchool.controllers;

import com.smartSchool.dtos.library.BookRequestDto;
import com.smartSchool.dtos.library.BookResponseDto;
import com.smartSchool.dtos.library.RackDto;
import com.smartSchool.services.BookService;
import com.smartSchool.services.RackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponseDto addBook(@Valid @RequestBody BookRequestDto request) {
        return bookService.addBook(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponseDto updateBook(@PathVariable Long id, @Valid @RequestBody BookRequestDto request) {
        return bookService.updateBook(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<BookResponseDto> searchBooks(@RequestParam(required = false) String title,
                                             @RequestParam(required = false) String author,
                                             @RequestParam(required = false) String subject,
                                             @RequestParam(required = false) String bookNumber,
                                             @RequestParam(required = false) String isbnNumber,
                                             @RequestParam(required = false) String publisherName,
                                             @RequestParam(required = false) String rackCode,
                                             Pageable pageable) {
        return bookService.searchBooks(title, author, subject, bookNumber, isbnNumber, publisherName, rackCode, pageable);
    }

    @GetMapping("/search/rack")
    @ResponseStatus(HttpStatus.OK)
    public Page<BookResponseDto> searchBooksByRack(@RequestParam(required = false) String rackCode,
                                                   @RequestParam(required = false) String location,
                                                   Pageable pageable) {
        return bookService.searchBooksByRack(rackCode, location, pageable);
    }

    @GetMapping("/search/combined")
    @ResponseStatus(HttpStatus.OK)
    public Page<BookResponseDto> searchBooksByRackPublisherAuthor(@RequestParam(required = false) String rackCode,
                                                                  @RequestParam(required = false) String location,
                                                                  @RequestParam(required = false) String publisherName,
                                                                  @RequestParam(required = false) String authorName,
                                                                  Pageable pageable) {
        return bookService.searchBooksByRackPublisherAuthor(rackCode, location, publisherName, authorName, pageable);
    }

}

