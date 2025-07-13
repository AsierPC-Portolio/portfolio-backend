package com.asierpc.portfoliobackend.adapter.rest;

import com.asierpc.portfoliobackend.adapter.mapper.ProjectMapper;
import com.asierpc.portfoliobackend.api.ProjectApi;
import com.asierpc.portfoliobackend.api.model.Project;
import com.asierpc.portfoliobackend.api.model.ProjectPage;
import com.asierpc.portfoliobackend.domain.project.dto.ProjectDto;
import com.asierpc.portfoliobackend.domain.project.port.ProjectServicePort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for Project endpoints.
 */
@RestController
@RequiredArgsConstructor
public class ProjectController implements ProjectApi {
  private final ProjectServicePort projectService;
  private final ProjectMapper projectMapper;

  @Override
  public ResponseEntity<ProjectPage> getProjects(
      String name, String tag, Integer page, Integer size, String sort
  ) {
    Page<ProjectDto> projects = projectService.getProjects(name, tag, PageRequest.of(page, size));
    List<Project> projectList = projects.getContent().stream()
        .map(projectMapper::toApiModel)
        .toList();
    ProjectPage projectPage = new ProjectPage();
    projectPage.setContent(projectList);
    projectPage.setTotalElements((int) projects.getTotalElements());
    projectPage.setTotalPages(projects.getTotalPages());
    return ResponseEntity.ok(
        projectPage
    );
  }

  @Override
  public ResponseEntity<Project> getProject(Integer id) {
    ProjectDto dto = projectService.getProject(id.longValue());
    Project apiModel = projectMapper.toApiModel(dto);
    return ResponseEntity.ok(apiModel);
  }

  @Override
  public ResponseEntity<Project> createProject(Project project) {
    ProjectDto dto = projectMapper.fromApiModel(project);
    ProjectDto created = projectService.createProject(dto);
    return new ResponseEntity<>(projectMapper.toApiModel(created), HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<Project> updateProject(Integer id, Project project) {
    ProjectDto dto = projectMapper.fromApiModel(project);
    ProjectDto updated = projectService.updateProject(id.longValue(), dto);
    return ResponseEntity.ok(projectMapper.toApiModel(updated));
  }

  @Override
  public ResponseEntity<Void> deleteProject(Integer id) {
    projectService.deleteProject(id.longValue());
    return ResponseEntity.noContent().build();
  }
}
