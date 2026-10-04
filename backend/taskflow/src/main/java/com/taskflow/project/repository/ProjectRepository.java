package com.taskflow.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskflow.project.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    
}
