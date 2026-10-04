package com.taskflow.project.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.taskflow.project.dto.ProjectResponse;
import com.taskflow.project.entity.Project;
import com.taskflow.project.repository.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ModelMapper modelMapper;
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
    }

	@Override
	public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(project -> modelMapper.map(project, ProjectResponse.class))
                .collect(Collectors.toList());
	}

	@Override
	public Optional<ProjectResponse> getProjectById(Long id) {
		return projectRepository.findById(id)
                .map(project -> modelMapper.map(project, ProjectResponse.class));
	}

	@Override
	public Project createProject(Project project) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'createProject'");
	}

	@Override
	public Project updateProject(Long id, Project project) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'updateProject'");
	}

	@Override
	public void deleteProject(Long id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'deleteProject'");
	}
}
