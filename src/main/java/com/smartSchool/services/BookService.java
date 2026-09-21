package com.smartSchool.services;

import com.smartSchool.dtos.library.BookRequestDto;
import com.smartSchool.dtos.library.BookResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    BookResponseDto addBook(BookRequestDto request);

    BookResponseDto updateBook(Long id, BookRequestDto request);

    void deleteBook(Long id);

    Page<BookResponseDto> searchBooks(String title,
                                      String author,
                                      String subject,
                                      String bookNumber,
                                      String isbnNumber,
                                      String publisherName,
                                      String rackCode,
                                      Pageable pageable);

    Page<BookResponseDto> searchBooksByRack(String rackCode,
                                            String location,
                                            Pageable pageable);

    Page<BookResponseDto> searchBooksByRackPublisherAuthor(String rackCode,
                                                           String location,
                                                           String publisherName,
                                                           String authorName,
                                                           Pageable pageable);
}
