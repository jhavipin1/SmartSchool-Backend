package com.smartSchool.repositories;

import com.smartSchool.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    // Simple field searches
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findBySubjectContainingIgnoreCase(String subject);
    Optional<Book> findByBookNumber(String bookNumber);
    Optional<Book> findByIsbnNumber(String isbnNumber);

    // Relationship-based searches (navigate into entity fields)
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByPublisherContainingIgnoreCase(String publisher);
    List<Book> findByRackNumberContainingIgnoreCase(String rackNumber);

}
