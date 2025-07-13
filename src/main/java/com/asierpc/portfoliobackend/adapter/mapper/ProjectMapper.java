package com.asierpc.portfoliobackend.adapter.mapper;

import com.asierpc.portfoliobackend.api.model.Project;
import com.asierpc.portfoliobackend.domain.project.dto.ProjectDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for Project and ProjectDto, including OpenAPI model mapping.
 */
@Mapper(componentModel = "spring")
public interface ProjectMapper {
  ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

  ProjectDto toDto(com.asierpc.portfoliobackend.domain.project.Project project);

  com.asierpc.portfoliobackend.domain.project.Project toEntity(ProjectDto dto);

  Project toApiModel(ProjectDto dto);

  ProjectDto fromApiModel(Project apiModel);

  // Helper methods to map String <-> URI
  default java.net.URI stringToUri(String value) {
    return value == null ? null : java.net.URI.create(value);
  }

  default String uriToString(java.net.URI uri) {
    return uri == null ? null : uri.toString();
  }

}
