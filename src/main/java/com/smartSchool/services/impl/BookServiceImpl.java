package com.smartSchool.services.impl;

import com.smartSchool.dtos.library.BookRequestDto;
import com.smartSchool.dtos.library.BookResponseDto;
import com.smartSchool.dtos.library.BookSpecification;
import com.smartSchool.entities.Book;
import com.smartSchool.repositories.BookRepository;
import com.smartSchool.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookResponseDto addBook(BookRequestDto request) {
        Book book = toEntity(request);
        Book saved = bookRepository.save(book);
        return toDto(saved);
    }

    @Override
    public BookResponseDto updateBook(Long id, BookRequestDto request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // update fields manually
        book.setTitle(request.getTitle());
        book.setBookNumber(request.getBookNumber());
        book.setIsbnNumber(request.getIsbnNumber());
        book.setSubject(request.getSubject());
        book.setQty(request.getQty());
        book.setPrice(request.getPrice());
        book.setPostDate(request.getPostDate());
        book.setDescription(request.getDescription());

        Book updated = bookRepository.save(book);
        return toDto(updated);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Page<BookResponseDto> searchBooks(String title, String author, String subject,
                                             String bookNumber, String isbnNumber,
                                             String publisherName, String rackCode,
                                             Pageable pageable) {

        Specification<Book> spec = Specification.where(null);

        if (title != null && !title.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }
        if (author != null && !author.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.join("author").get("name")), "%" + author.toLowerCase() + "%"));
        }
        if (subject != null && !subject.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("subject")), "%" + subject.toLowerCase() + "%"));
        }
        if (bookNumber != null && !bookNumber.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("bookNumber"), bookNumber));
        }
        if (isbnNumber != null && !isbnNumber.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("isbnNumber"), isbnNumber));
        }
        if (publisherName != null && !publisherName.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.join("publisher").get("name")), "%" + publisherName.toLowerCase() + "%"));
        }
        if (rackCode != null && !rackCode.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.join("rack").get("rackCode")), "%" + rackCode.toLowerCase() + "%"));
        }

        Page<Book> bookPage = bookRepository.findAll(spec, pageable);
        return bookPage.map(this::toDto);
    }

    @Override
    public Page<BookResponseDto> searchBooksByRack(String rackCode, String location, Pageable pageable) {
        Specification<Book> spec = Specification.where(BookSpecification.hasRackCode(rackCode))
                .and(BookSpecification.hasRackLocation(location));

        Page<Book> bookPage = bookRepository.findAll(spec, pageable);
        return bookPage.map(this::toDto);
    }

    @Override
    public Page<BookResponseDto> searchBooksByRackPublisherAuthor(String rackCode,
                                                                  String location,
                                                                  String publisherName,
                                                                  String authorName,
                                                                  Pageable pageable) {
        Specification<Book> spec = Specification.where(BookSpecification.hasRackCode(rackCode))
                .and(BookSpecification.hasRackLocation(location))
                .and(BookSpecification.hasPublisherName(publisherName))
                .and(BookSpecification.hasAuthorName(authorName));

        Page<Book> bookPage = bookRepository.findAll(spec, pageable);
        return bookPage.map(this::toDto);
    }

    // ✅ Manual mapping methods
    private Book toEntity(BookRequestDto dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setBookNumber(dto.getBookNumber());
        book.setIsbnNumber(dto.getIsbnNumber());
        book.setSubject(dto.getSubject());
        book.setQty(dto.getQty());
        book.setPrice(dto.getPrice());
        book.setPostDate(dto.getPostDate());
        book.setDescription(dto.getDescription());
        // handle author/publisher/rack if they are Strings or entities
        return book;
    }

    private BookResponseDto toDto(Book book) {
        if (book == null) {
            return null;
        }

        return BookResponseDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .bookNumber(book.getBookNumber())
                .isbnNumber(book.getIsbnNumber())
                .publisher(book.getPublisher())
                .author(book.getAuthor())
                .subject(book.getSubject())
                .rackCode(book.getRack() != null ? book.getRack().getRackCode() : null)
                .qty(book.getQty())
                .availableQty(book.getAvailableQty())
                .price(book.getPrice())
                .postDate(book.getPostDate())
                .description(book.getDescription())
                .build();
    }
}
