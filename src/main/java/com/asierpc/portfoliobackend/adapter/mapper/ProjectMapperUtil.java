package com.asierpc.portfoliobackend.adapter.mapper;

import com.asierpc.portfoliobackend.domain.project.Project;
import com.asierpc.portfoliobackend.domain.project.dto.ProjectDto;

/**
 * Utility class for mapping between Project domain and DTO.
 */
public class ProjectMapperUtil {

  private ProjectMapperUtil() {
    // Utility class
  }

  /**
   * Maps a Project domain entity to a ProjectDto.
   *
   * @param project the domain entity
   * @return the DTO, or null if input is null
   */
  public static ProjectDto toDto(Project project) {
    if (project == null) {
      return null;
    }
    return ProjectDto.builder()
        .id(project.getId())
        .name(project.getName())
        .description(project.getDescription())
        .countryCode(project.getCountryCode())
        .link(project.getLink())
        .startDate(project.getStartDate())
        .endDate(project.getEndDate())
        .build();
  }

  /**
   * Maps a ProjectDto to a Project domain entity.
   *
   * @param dto the DTO
   * @return the domain entity, or null if input is null
   */
  public static Project fromDto(ProjectDto dto) {
    if (dto == null) {
      return null;
    }
    return Project.builder()
        .id(dto.getId())
        .name(dto.getName())
        .description(dto.getDescription())
        .countryCode(dto.getCountryCode())
        .link(dto.getLink())
        .startDate(dto.getStartDate())
        .endDate(dto.getEndDate())
        .build();
  }
}
