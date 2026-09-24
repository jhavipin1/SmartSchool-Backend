package com.smartSchool.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student_exams")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "marks_obtained")
    private Integer marksObtained;
}