package com.asierpc.portfoliobackend.application.project;

import com.asierpc.portfoliobackend.adapter.mapper.ProjectMapper;
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
  private final ProjectMapper projectMapper;

  @Override
  public Page<ProjectDto> getProjects(String name, String tag, Pageable pageable) {
    boolean noName = (name == null || name.isBlank());
    boolean noTag = (tag == null || tag.isBlank());
    Page<Project> page;
    if (noName && noTag) {
      page = projectRepository.findAll(pageable);
    } else {
      page = projectRepository.findByNameContainingIgnoreCaseAndTagsContainingIgnoreCase(
        noName ? "" : name,
        noTag ? "" : tag,
        pageable
      );
    }
    return page.map(projectMapper::toDto);
  }

  @Override
  public ProjectDto getProject(Long id) {
    Project project = projectRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Project not found"));
    return projectMapper.toDto(project);
  }

  @Override
  public ProjectDto createProject(ProjectDto dto) {
    Project project = projectMapper.toEntity(dto);
    Project saved = projectRepository.save(project);
    return projectMapper.toDto(saved);
  }

  @Override
  public ProjectDto updateProject(Long id, ProjectDto dto) {
    projectRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Project not found"));
    Project project = projectMapper.toEntity(dto);
    project.setId(id); // Ensure the ID is set for the update
    Project saved = projectRepository.save(project);
    return projectMapper.toDto(saved);
  }

  @Override
  public void deleteProject(Long id) {
    projectRepository.deleteById(id);
  }
}
