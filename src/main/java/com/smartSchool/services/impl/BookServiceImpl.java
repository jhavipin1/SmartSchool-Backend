package com.smartSchool.services.impl;

import com.smartSchool.dtos.library.BookRequestDto;
import com.smartSchool.dtos.library.BookResponseDto;
import com.smartSchool.dtos.library.BookSpecification;
import com.smartSchool.entities.Book;
import com.smartSchool.entities.Rack;
import com.smartSchool.repositories.BookRepository;
import com.smartSchool.repositories.RackRepository;
import com.smartSchool.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final RackRepository rackRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> getAllBooksUnpaginated() {
        return bookRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookResponseDto> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(this::toDto);
    }

    @Override
    public BookResponseDto addBook(BookRequestDto request) {
        Book book = toEntity(request);
        Book saved = bookRepository.save(book);
        return toDto(saved);
    }

    @Override
    public BookResponseDto updateBook(Long id, BookRequestDto request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        // Update basic fields
        book.setTitle(request.getTitle());
        book.setBookNumber(request.getBookNumber());
        book.setIsbnNumber(request.getIsbnNumber());
        book.setSubject(request.getSubject());
        book.setAuthor(request.getAuthor());
        book.setPublisher(request.getPublisher());

        // FIX: Update Rack relationship using Rack object
        if (request.getRackCode() != null && !request.getRackCode().trim().isEmpty()) {
            Rack rack = rackRepository.findByRackCode(request.getRackCode().trim()).orElse(null);
            book.setRack(rack);
        } else {
            book.setRack(null);
        }

        // Update quantities and other fields...
        Book updated = bookRepository.save(book);
        return toDto(updated);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookResponseDto> searchBooks(String title, String author, String subject,
                                             String bookNumber, String isbnNumber,
                                             String publisherName, String rackCode,
                                             Pageable pageable) {

        Specification<Book> spec = Specification.where(null);

        if (title != null && !title.trim().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("title")), "%" + title.trim().toLowerCase() + "%"));
        }
        if (author != null && !author.trim().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.join("author").get("name")), "%" + author.trim().toLowerCase() + "%"));
        }
        if (subject != null && !subject.trim().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("subject")), "%" + subject.trim().toLowerCase() + "%"));
        }
        if (bookNumber != null && !bookNumber.trim().isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("bookNumber"), bookNumber.trim()));
        }
        if (isbnNumber != null && !isbnNumber.trim().isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("isbnNumber"), isbnNumber.trim()));
        }
        if (publisherName != null && !publisherName.trim().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.join("publisher").get("name")), "%" + publisherName.trim().toLowerCase() + "%"));
        }
        if (rackCode != null && !rackCode.trim().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.join("rack").get("rackCode")), "%" + rackCode.trim().toLowerCase() + "%"));
        }

        Page<Book> bookPage = bookRepository.findAll(spec, pageable);
        return bookPage.map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookResponseDto> searchBooksByRack(String rackCode, String location, Pageable pageable) {
        Specification<Book> spec = Specification.where(BookSpecification.hasRackCode(rackCode))
                .and(BookSpecification.hasRackLocation(location));

        Page<Book> bookPage = bookRepository.findAll(spec, pageable);
        return bookPage.map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
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

    private Book toEntity(BookRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setBookNumber(dto.getBookNumber());
        book.setIsbnNumber(dto.getIsbnNumber());
        book.setSubject(dto.getSubject());
        book.setAuthor(dto.getAuthor());
        book.setPublisher(dto.getPublisher());

        // FIX: Fetch the Rack entity using rackCode from DTO
        if (dto.getRackCode() != null && !dto.getRackCode().trim().isEmpty()) {
            Rack rack = rackRepository.findByRackCode(dto.getRackCode().trim())
                    .orElse(null); // Or throw an exception if rack must exist
            book.setRack(rack);
        } else {
            book.setRack(null);
        }

        Integer quantity = dto.getQty() != null ? dto.getQty() : 0;
        book.setQty(quantity);
        book.setAvailableQty(dto.getQty() != null ? dto.getQty() : quantity);

        book.setPrice(dto.getPrice());
        book.setPostDate(dto.getPostDate());
        book.setDescription(dto.getDescription());
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