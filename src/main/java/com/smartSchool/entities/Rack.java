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
    @Column(name = "rack_code", nullable = false, unique = true)
    private String rackCode;

    private String location;

    @OneToMany(mappedBy = "rack", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Book> books = new ArrayList<>();
}