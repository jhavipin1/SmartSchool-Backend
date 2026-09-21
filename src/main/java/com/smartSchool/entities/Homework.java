package com.smartSchool.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "homeworks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Homework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Class is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private ClassName className;

    @NotNull(message = "Section is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @NotNull(message = "Subject is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @NotNull(message = "Homework date is required")
    @Column(name = "homework_date", nullable = false)
    private LocalDate homeworkDate;

    @NotNull(message = "Submission date is required")
    @Column(name = "submission_date", nullable = false)
    private LocalDate submissionDate;

    @Column(name = "evaluation_date")
    private LocalDate evaluationDate;

    @Column(name = "max_marks")
    private Integer maxMarks;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "document_path")
    private String documentPath;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "active")
    private Boolean active = true;
}
