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

    @Column(unique = true)
    private String bookNumber;

    private String isbnNumber;
    private String publisher;
    private String author;
    private String subject;
    private String rackNumber;
    @Column(name = "qty", nullable = false)
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer qty;

    @Column(name = "available_qty", nullable = false)
    @Min(value = 0, message = "Available quantity cannot be negative")
    private Integer availableQty;
    private Double price;

    private LocalDate postDate;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "rack_id")
    private Rack rack;
}
