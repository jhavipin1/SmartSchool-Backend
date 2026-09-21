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
public class BookRequestDto {
    private String title;
    private String bookNumber;
    private String isbnNumber;
    private String publisher;
    private String author;
    private String subject;
    private String rackNumber;
    private Integer qty;
    private Double price;
    private LocalDate postDate;
    private String description;
}
