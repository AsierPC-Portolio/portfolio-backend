package com.asierpc.portfoliobackend.adapter.rest;

import com.asierpc.portfoliobackend.api.model.Project;
import com.asierpc.portfoliobackend.api.model.ProjectPage;
import com.asierpc.portfoliobackend.domain.project.dto.ProjectDto;
import com.asierpc.portfoliobackend.domain.project.port.ProjectServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectControllerTest {
  @Mock
  private ProjectServicePort projectService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  // ---
  @Test
  void getProjects_returnsProjectPage() {
    ProjectDto dto = ProjectDto.builder().id(1L).name("Test").build();
    List<ProjectDto> dtoList = Arrays.asList(dto);
    Page<ProjectDto> page = new PageImpl<>(dtoList);
    when(projectService.getProjects(null, PageRequest.of(0, 10))).thenReturn(page);

    ProjectController controller = new ProjectController(projectService);
    ResponseEntity<ProjectPage> response = controller.getProjects(null, 0, 10, "id,asc");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1, response.getBody().getContent().size());
    assertEquals("Test", response.getBody().getContent().get(0).getName());
  }
  @Test
  void getProject_returnsProject() {
    ProjectDto dto = ProjectDto.builder().id(1L).name("Test").build();
    when(projectService.getProject(1L)).thenReturn(dto);

    ProjectController controller = new ProjectController(projectService);
    ResponseEntity<Project> response = controller.getProject(1);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Test", response.getBody().getName());
  }

  // ---
  @Test
  void createProject_returnsCreated() {
    Project apiModel = new Project().id(1L).name("Test");
    ProjectDto dto = ProjectDto.builder().id(1L).name("Test").build();
    when(projectService.createProject(any(ProjectDto.class))).thenReturn(dto);

    ProjectController controller = new ProjectController(projectService);
    ResponseEntity<Project> response = controller.createProject(apiModel);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals("Test", response.getBody().getName());
  }

  @Test
  void updateProject_returnsOk() {
    Project apiModel = new Project().id(1L).name("Updated");
    ProjectDto dto = ProjectDto.builder().id(1L).name("Updated").build();
    when(projectService.updateProject(eq(1L), any(ProjectDto.class))).thenReturn(dto);

    ProjectController controller = new ProjectController(projectService);
    ResponseEntity<Project> response = controller.updateProject(1, apiModel);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Updated", response.getBody().getName());
  }

  @Test
  void deleteProject_returnsNoContent() {
    doNothing().when(projectService).deleteProject(1L);
    ProjectController controller = new ProjectController(projectService);
    ResponseEntity<Void> response = controller.deleteProject(1);
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(projectService, times(1)).deleteProject(1L);
  }
}
