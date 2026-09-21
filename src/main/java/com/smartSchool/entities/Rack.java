package com.smartSchool.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "racks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String rackCode;

    private String location;

    @OneToMany(mappedBy = "rackNumber", cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();
}

