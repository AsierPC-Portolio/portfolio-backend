package com.asierpc.portfoliobackend.domain.project.port;

import com.asierpc.portfoliobackend.domain.project.dto.ProjectDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Domain port for Project CRUD operations.
 */
public interface ProjectServicePort {
  Page<ProjectDto> getProjects(String name, Pageable pageable);

  ProjectDto getProject(Long id);

  ProjectDto createProject(ProjectDto dto);

  ProjectDto updateProject(Long id, ProjectDto dto);

  void deleteProject(Long id);
}
