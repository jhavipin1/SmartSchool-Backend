package com.smartSchool.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String typeName; // e.g. Midterm, Final

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_group_id")
    private ExamGroup examGroup;

    @OneToMany(mappedBy = "examType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamSubject> examSubjects;
}
