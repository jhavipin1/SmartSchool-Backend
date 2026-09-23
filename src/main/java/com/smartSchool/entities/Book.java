package com.smartSchool.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Book title is required")
    private String title;

    @Column(name = "book_number", unique = true)
    private String bookNumber;

    @Column(name = "isbn_number")
    private String isbnNumber;

    private String publisher;
    private String author;
    private String subject;

    @Column(name = "qty", nullable = false)
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer qty;

    @Column(name = "available_qty", nullable = false)
    @Min(value = 0, message = "Available quantity cannot be negative")
    private Integer availableQty;

    private Double price;

    @Column(name = "post_date")
    private LocalDate postDate;

    @Column(length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rack_code", referencedColumnName = "rack_code")
    private Rack rack;
}