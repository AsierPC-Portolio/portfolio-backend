package com.asierpc.portfoliobackend.adapter.rest;

import com.asierpc.portfoliobackend.api.model.Project;
import com.asierpc.portfoliobackend.api.model.ProjectPage;
import com.asierpc.portfoliobackend.domain.project.dto.ProjectDto;
import com.asierpc.portfoliobackend.domain.project.port.ProjectServicePort;
import com.asierpc.portfoliobackend.adapter.mapper.ProjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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
    @Mock
    private ProjectMapper projectMapper;
    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProjects_returnsProjectPage() {
        ProjectDto dto = ProjectDto.builder().id(1L).name("Test").build();
        Project apiModel = new Project().id(1L).name("Test");
        List<ProjectDto> dtoList = Arrays.asList(dto);
        Page<ProjectDto> page = new PageImpl<>(dtoList);
        when(projectService.getProjects(null, PageRequest.of(0, 10))).thenReturn(page);
        when(projectMapper.toApiModel(dto)).thenReturn(apiModel);

        ResponseEntity<ProjectPage> response = projectController.getProjects(null, 0, 10, "id,asc");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals("Test", response.getBody().getContent().get(0).getName());
    }

    @Test
    void getProject_returnsProject() {
        ProjectDto dto = ProjectDto.builder().id(1L).name("Test").build();
        Project apiModel = new Project().id(1L).name("Test");
        when(projectService.getProject(1L)).thenReturn(dto);
        when(projectMapper.toApiModel(dto)).thenReturn(apiModel);

        ResponseEntity<Project> response = projectController.getProject(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test", response.getBody().getName());
    }

    @Test
    void createProject_returnsCreated() {
        Project apiModel = new Project().id(1L).name("Test");
        ProjectDto dto = ProjectDto.builder().id(1L).name("Test").build();
        when(projectMapper.fromApiModel(apiModel)).thenReturn(dto);
        when(projectService.createProject(dto)).thenReturn(dto);
        when(projectMapper.toApiModel(dto)).thenReturn(apiModel);

        ResponseEntity<Project> response = projectController.createProject(apiModel);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Test", response.getBody().getName());
    }

    @Test
    void updateProject_returnsOk() {
        Project apiModel = new Project().id(1L).name("Updated");
        ProjectDto dto = ProjectDto.builder().id(1L).name("Updated").build();
        when(projectMapper.fromApiModel(apiModel)).thenReturn(dto);
        when(projectService.updateProject(1L, dto)).thenReturn(dto);
        when(projectMapper.toApiModel(dto)).thenReturn(apiModel);

        ResponseEntity<Project> response = projectController.updateProject(1, apiModel);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated", response.getBody().getName());
    }

    @Test
    void deleteProject_returnsNoContent() {
        doNothing().when(projectService).deleteProject(1L);
        ResponseEntity<Void> response = projectController.deleteProject(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(projectService, times(1)).deleteProject(1L);
    }
    
}
