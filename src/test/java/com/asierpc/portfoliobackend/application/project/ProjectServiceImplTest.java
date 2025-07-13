package com.asierpc.portfoliobackend.application.project;

import com.asierpc.portfoliobackend.domain.project.Project;
import com.asierpc.portfoliobackend.domain.project.dto.ProjectDto;
import com.asierpc.portfoliobackend.infrastructure.repository.ProjectRepository;
import com.asierpc.portfoliobackend.adapter.mapper.ProjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceImplTest {
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectMapper projectMapper;
    @InjectMocks
    private ProjectServiceImpl projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProjects_returnsPageOfDtos() {
        Project project = Project.builder().id(1L).name("Test").build();
        ProjectDto dto = ProjectDto.builder().id(1L).name("Test").build();
        Pageable pageable = PageRequest.of(0, 10);
        when(projectMapper.toDto(project)).thenReturn(dto);

        Page<ProjectDto> result = projectService.getProjects(null, pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals("Test", result.getContent().get(0).getName());
    }

    @Test
    void getProject_found_returnsDto() {
        Project project = Project.builder().id(1L).name("Test").build();
        ProjectDto dto = ProjectDto.builder().id(1L).name("Test").build();
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectMapper.toDto(project)).thenReturn(dto);

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
        Project project = Project.builder().id(1L).name("Test").build();
        when(projectMapper.toEntity(dto)).thenReturn(project);
        when(projectRepository.save(project)).thenReturn(project);
        when(projectMapper.toDto(project)).thenReturn(dto);

        ProjectDto result = projectService.createProject(dto);
        assertEquals("Test", result.getName());
    }

    @Test
    void updateProject_found_savesAndReturnsDto() {
        ProjectDto dto = ProjectDto.builder().id(1L).name("Updated").build();
        Project project = Project.builder().id(1L).name("Old").build();
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectMapper.toEntity(dto)).thenReturn(project);
        when(projectRepository.save(project)).thenReturn(project);
        when(projectMapper.toDto(project)).thenReturn(dto);

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
}
