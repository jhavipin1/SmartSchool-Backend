package com.smartSchool.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "designations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Designation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "designation_name", nullable = false, unique = true, length = 100)
    private String designationName;

    @Column(name = "description", length = 255)
    private String description;
}