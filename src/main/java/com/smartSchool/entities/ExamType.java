package com.smartSchool.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exam_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_name", nullable = false)
    private String typeName; // e.g. "Theory", "Practical"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_group_id", nullable = false)
    private ExamGroup examGroup;

    @OneToMany(mappedBy = "examType", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Subject> subjects = new ArrayList<>();
}
