package com.smartSchool.dtos.library;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDto {
    private Long id;
    private String title;
    private String bookNumber;
    private String isbnNumber;
    private String publisher;
    private String author;
    private String subject;
    private String rackCode;
    private Integer qty;
    private Integer availableQty;
    private Double price;
    private LocalDate postDate;
    private String description;
}