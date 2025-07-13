package com.asierpc.portfoliobackend.application.project;

import com.asierpc.portfoliobackend.adapter.mapper.ProjectMapperUtil;
import com.asierpc.portfoliobackend.domain.project.Project;
import com.asierpc.portfoliobackend.domain.project.dto.ProjectDto;
import com.asierpc.portfoliobackend.domain.project.port.ProjectServicePort;
import com.asierpc.portfoliobackend.infrastructure.repository.ProjectRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Project use case implementation.
 */
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectServicePort {
  private final ProjectRepository projectRepository;

  @Override
  public Page<ProjectDto> getProjects(String name, Pageable pageable) {
    boolean noName = (name == null || name.isBlank());
    Page<Project> page;
    if (noName) {
      page = projectRepository.findAll(pageable);
    } else {
      page = projectRepository.findByNameContainingIgnoreCase(
      name,
      pageable
      );
    }
    return page.map(ProjectMapperUtil::toDto);
  }

  @Override
  public ProjectDto getProject(Long id) {
    Project project = projectRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Project not found"));
    return ProjectMapperUtil.toDto(project);
  }

  @Override
  public ProjectDto createProject(ProjectDto dto) {
    Project project = ProjectMapperUtil.fromDto(dto);
    Project saved = projectRepository.save(project);
    return ProjectMapperUtil.toDto(saved);
  }

  @Override
  public ProjectDto updateProject(Long id, ProjectDto dto) {
    projectRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Project not found"));
    Project project = ProjectMapperUtil.fromDto(dto);
    project.setId(id); // Ensure the ID is set for the update
    Project saved = projectRepository.save(project);
    return ProjectMapperUtil.toDto(saved);
  }

  @Override
  public void deleteProject(Long id) {
    projectRepository.deleteById(id);
  }
}
