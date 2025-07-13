package com.asierpc.portfoliobackend.adapter.mapper;

import com.asierpc.portfoliobackend.api.model.Project;
import com.asierpc.portfoliobackend.domain.project.dto.ProjectDto;

/**
 * Mapper util for ProjectDto <-> OpenAPI Project model.
 */
public class ProjectApiMapperUtil {

  private ProjectApiMapperUtil() {
    // Utility class
  }

  /**
   * Maps a ProjectDto to an OpenAPI Project model.
   *
   * @param dto the DTO
   * @return the OpenAPI Project, or null if input is null
   */
  public static Project toApiModel(ProjectDto dto) {
    if (dto == null) {
      return null;
    }
    Project p = new Project();
    p.setId(dto.getId());
    p.setName(dto.getName());
    p.setDescription(dto.getDescription());
    p.setCountryCode(dto.getCountryCode());
    // String to URI
    if (dto.getLink() != null) {
      p.setLink(java.net.URI.create(dto.getLink()));
    }
    p.setStartDate(dto.getStartDate());
    p.setEndDate(dto.getEndDate());
    return p;
  }

  /**
   * Maps an OpenAPI Project model to a ProjectDto.
   *
   * @param api the OpenAPI Project
   * @return the DTO, or null if input is null
   */
  public static ProjectDto fromApiModel(Project api) {
    if (api == null) {
      return null;
    }
    return ProjectDto.builder()
        .id(api.getId())
        .name(api.getName())
        .description(api.getDescription())
        .countryCode(api.getCountryCode())
        // URI to String
        .link(api.getLink() != null ? api.getLink().toString() : null)
        .startDate(api.getStartDate())
        .endDate(api.getEndDate())
        .build();
  }
}
