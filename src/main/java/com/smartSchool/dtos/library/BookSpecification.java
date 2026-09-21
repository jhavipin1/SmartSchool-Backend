package com.smartSchool.dtos.library;

import com.smartSchool.entities.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<Book> hasRackCode(String rackCode) {
        return (root, query, cb) -> rackCode == null ? null :
                cb.like(cb.lower(root.join("rackNumber").get("rackCode")), "%" + rackCode.toLowerCase() + "%");
    }

    public static Specification<Book> hasRackLocation(String location) {
        return (root, query, cb) -> location == null ? null :
                cb.like(cb.lower(root.join("rackNumber").get("location")), "%" + location.toLowerCase() + "%");
    }

    public static Specification<Book> hasPublisherName(String publisherName) {
        return (root, query, cb) -> publisherName == null ? null :
                cb.like(cb.lower(root.join("publisher").get("name")), "%" + publisherName.toLowerCase() + "%");
    }

    public static Specification<Book> hasAuthorName(String authorName) {
        return (root, query, cb) -> authorName == null ? null :
                cb.like(cb.lower(root.join("author").get("name")), "%" + authorName.toLowerCase() + "%");
    }
}
