package com.asierpc.portfoliobackend.adapter.mapper;

import com.asierpc.portfoliobackend.domain.project.Project;
import com.asierpc.portfoliobackend.domain.project.dto.ProjectDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProjectMapperUtilTest {
  @Test
  void toDto_and_fromDto_workCorrectly() {
    Project project = Project.builder()
        .id(1L)
        .name("Test")
        .description("desc")
        .countryCode("ES")
        .link("https://test.com")
        .startDate(LocalDate.of(2020,1,1))
        .endDate(LocalDate.of(2021,1,1))
        .build();
    ProjectDto dto = ProjectMapperUtil.toDto(project);
    assertEquals(project.getId(), dto.getId());
    assertEquals(project.getName(), dto.getName());
    assertEquals(project.getDescription(), dto.getDescription());
    assertEquals(project.getCountryCode(), dto.getCountryCode());
    assertEquals(project.getLink(), dto.getLink());
    assertEquals(project.getStartDate(), dto.getStartDate());
    assertEquals(project.getEndDate(), dto.getEndDate());

    Project project2 = ProjectMapperUtil.fromDto(dto);
    assertEquals(dto.getId(), project2.getId());
    assertEquals(dto.getName(), project2.getName());
    assertEquals(dto.getDescription(), project2.getDescription());
    assertEquals(dto.getCountryCode(), project2.getCountryCode());
    assertEquals(dto.getLink(), project2.getLink());
    assertEquals(dto.getStartDate(), project2.getStartDate());
    assertEquals(dto.getEndDate(), project2.getEndDate());
  }

  @Test
  void nullSafe() {
    assertNull(ProjectMapperUtil.toDto(null));
    assertNull(ProjectMapperUtil.fromDto(null));
  }
}
