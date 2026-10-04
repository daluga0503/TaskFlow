package com.taskflow.project.entity;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "projects")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String description;

    @Builder.Default
    private Status status = Status.INACTIVE;

    @NotNull
    // EL propietario debe ser miembro del proyecto, por lo que se almacena el ID del usuario propietario
    private Long ownerId;

    private List<Long> members;

    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;
    @Column(nullable = false)
    private Timestamp updatedAt;
    
}

enum Status {
    INACTIVE,
    ACTIVE,
    ARCHIVED,
    COMPLETED,
    DELETED
}
