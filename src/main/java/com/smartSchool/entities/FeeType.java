package com.smartSchool.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "fee_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Fee Group is required")
    @Size(max = 100, message = "Fee Group must not exceed 100 characters")
    @Column(name = "fee_group", nullable = false, length = 100)
    private String feeGroup;

    @NotBlank(message = "Fee Name is required")
    @Size(max = 100, message = "Fee Name must not exceed 100 characters")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Fee Code is required")
    @Size(max = 50, message = "Fee Code must not exceed 50 characters")
    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Column(name = "description", length = 500)
    private String description;
}