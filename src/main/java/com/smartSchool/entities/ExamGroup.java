package com.smartSchool.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exam_groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // e.g. "Annual", "First Term"

    @OneToMany(mappedBy = "examGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ExamType> examTypes = new ArrayList<>();
}
