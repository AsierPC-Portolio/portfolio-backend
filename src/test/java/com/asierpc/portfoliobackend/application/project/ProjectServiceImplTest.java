package com.asierpc.portfoliobackend.application.project;

import com.asierpc.portfoliobackend.domain.project.Project;
import com.asierpc.portfoliobackend.domain.project.dto.ProjectDto;
import com.asierpc.portfoliobackend.infrastructure.repository.ProjectRepository;
import com.asierpc.portfoliobackend.adapter.mapper.ProjectMapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class ProjectServiceImplTest {
  @Mock
  private ProjectRepository projectRepository;
  @InjectMocks
  private ProjectServiceImpl projectService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getProject_found_returnsDto() {
    Project project = Project.builder().id(1L).name("Test").build();
    when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
    
    ProjectDto result = projectService.getProject(1L);
    assertEquals("Test", result.getName());
  }

  @Test
  void getProject_notFound_throws() {
    when(projectRepository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(NoSuchElementException.class, () -> projectService.getProject(1L));
  }

  @Test
  void createProject_savesAndReturnsDto() {
    ProjectDto dto = ProjectDto.builder().id(1L).name("Test").build();
    Project project = ProjectMapperUtil.fromDto(dto);
    when(projectRepository.save(project)).thenReturn(project);
    // Usar ProjectMapperUtil.toDto(project) directamente

    ProjectDto result = projectService.createProject(dto);
    assertEquals("Test", result.getName());
  }

  @Test
  void updateProject_found_savesAndReturnsDto() {
    ProjectDto dto = ProjectDto.builder().id(1L).name("Updated").build();
    Project oldProject = Project.builder().id(1L).name("Old").build();
    Project updatedProject = ProjectMapperUtil.fromDto(dto);
    when(projectRepository.findById(1L)).thenReturn(Optional.of(oldProject));
    when(projectRepository.save(any(Project.class))).thenReturn(updatedProject);

    ProjectDto result = projectService.updateProject(1L, dto);
    assertEquals("Updated", result.getName());
  }

  @Test
  void updateProject_notFound_throws() {
    ProjectDto dto = ProjectDto.builder().id(1L).name("Updated").build();
    when(projectRepository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(NoSuchElementException.class, () -> projectService.updateProject(1L, dto));
  }

  @Test
  void deleteProject_deletesById() {
    doNothing().when(projectRepository).deleteById(1L);
    assertDoesNotThrow(() -> projectService.deleteProject(1L));
    verify(projectRepository, times(1)).deleteById(1L);
  }

  @Test
  void getProjects_noName_returnsAllProjects() {
  Pageable pageable = PageRequest.of(0, 10);
  Project project1 = Project.builder().id(1L).name("Alpha").build();
  Project project2 = Project.builder().id(2L).name("Beta").build();
  List<Project> projects = Arrays.asList(project1, project2);
  Page<Project> projectPage = new PageImpl<>(projects, pageable, projects.size());

  when(projectRepository.findAll(pageable)).thenReturn(projectPage);

  Page<ProjectDto> result = projectService.getProjects(null, pageable);

  assertEquals(2, result.getTotalElements());
  assertEquals("Alpha", result.getContent().get(0).getName());
  assertEquals("Beta", result.getContent().get(1).getName());
  }

  @Test
  void getProjects_blankName_returnsAllProjects() {
  Pageable pageable = PageRequest.of(0, 10);
  Project project = Project.builder().id(1L).name("Alpha").build();
  Page<Project> projectPage = new PageImpl<>(Collections.singletonList(project), pageable, 1);

  when(projectRepository.findAll(pageable)).thenReturn(projectPage);

  Page<ProjectDto> result = projectService.getProjects("   ", pageable);

  assertEquals(1, result.getTotalElements());
  assertEquals("Alpha", result.getContent().get(0).getName());
  }

  @Test
  void getProjects_withName_returnsFilteredProjects() {
  Pageable pageable = PageRequest.of(0, 10);
  Project project = Project.builder().id(1L).name("Test Project").build();
  Page<Project> projectPage = new PageImpl<>(Collections.singletonList(project), pageable, 1);

  when(projectRepository.findByNameContainingIgnoreCase("Test", pageable)).thenReturn(projectPage);

  Page<ProjectDto> result = projectService.getProjects("Test", pageable);

  assertEquals(1, result.getTotalElements());
  assertEquals("Test Project", result.getContent().get(0).getName());
  }

  @Test
  void getProjects_withName_noResults_returnsEmptyPage() {
  Pageable pageable = PageRequest.of(0, 10);
  Page<Project> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

  when(projectRepository.findByNameContainingIgnoreCase("Nonexistent", pageable)).thenReturn(emptyPage);

  Page<ProjectDto> result = projectService.getProjects("Nonexistent", pageable);

  assertEquals(0, result.getTotalElements());
  assertTrue(result.getContent().isEmpty());
  }
}