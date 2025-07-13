package com.asierpc.portfoliobackend.adapter.mapper;

import com.asierpc.portfoliobackend.api.model.Project;
import com.asierpc.portfoliobackend.domain.project.dto.ProjectDto;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProjectApiMapperUtilTest {
  @Test
  void toApiModel_and_fromApiModel_workCorrectly() {
    ProjectDto dto = ProjectDto.builder()
        .id(1L)
        .name("Test")
        .description("desc")
        .countryCode("ES")
        .link("https://test.com")
        .startDate(LocalDate.of(2020,1,1))
        .endDate(LocalDate.of(2021,1,1))
        .build();
    Project api = ProjectApiMapperUtil.toApiModel(dto);
    assertEquals(dto.getId(), api.getId());
    assertEquals(dto.getName(), api.getName());
    assertEquals(dto.getDescription(), api.getDescription());
    assertEquals(dto.getCountryCode(), api.getCountryCode());
    assertEquals(dto.getLink(), api.getLink() != null ? api.getLink().toString() : null);
    assertEquals(dto.getStartDate(), api.getStartDate());
    assertEquals(dto.getEndDate(), api.getEndDate());

    ProjectDto dto2 = ProjectApiMapperUtil.fromApiModel(api);
    assertEquals(api.getId(), dto2.getId());
    assertEquals(api.getName(), dto2.getName());
    assertEquals(api.getDescription(), dto2.getDescription());
    assertEquals(api.getCountryCode(), dto2.getCountryCode());
    assertEquals(api.getLink() != null ? api.getLink().toString() : null, dto2.getLink());
    assertEquals(api.getStartDate(), dto2.getStartDate());
    assertEquals(api.getEndDate(), dto2.getEndDate());
  }

  @Test
  void nullSafe() {
    assertNull(ProjectApiMapperUtil.toApiModel(null));
    assertNull(ProjectApiMapperUtil.fromApiModel(null));
  }

  @Test
  void stringToUri_and_uriToString() {
    String url = "https://test.com";
    URI uri = URI.create(url);
    Project api = new Project();
    api.setLink(uri);
    ProjectDto dto = ProjectApiMapperUtil.fromApiModel(api);
    assertEquals(url, dto.getLink());
    Project api2 = ProjectApiMapperUtil.toApiModel(dto);
    assertEquals(uri, api2.getLink());
  }
}
