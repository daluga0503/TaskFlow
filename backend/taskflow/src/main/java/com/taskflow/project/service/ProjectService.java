package com.taskflow.project.service;

import java.util.List;
import java.util.Optional;

import com.taskflow.project.dto.ProjectResponse;
import com.taskflow.project.entity.Project;

public interface ProjectService {
    List<ProjectResponse> getAllProjects();
    Optional<ProjectResponse> getProjectById(Long id);
    Project createProject(Project project);
    Project updateProject(Long id, Project project);
    void deleteProject(Long id);
}
