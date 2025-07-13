package com.asierpc.portfoliobackend.adapter.mapper;

import com.asierpc.portfoliobackend.api.model.Project;
import com.asierpc.portfoliobackend.domain.project.dto.ProjectDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for Project and ProjectDto, including OpenAPI model mapping and tag conversions.
 */
@Mapper(componentModel = "spring")
public interface ProjectMapper {
  ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

  @Mapping(target = "tags", source = "tags", qualifiedByName = "csvToArray")
  ProjectDto toDto(com.asierpc.portfoliobackend.domain.project.Project project);

  @Mapping(target = "tags", source = "tags", qualifiedByName = "arrayToCsv")
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

  @Named("csvToArray")
  static String[] csvToArray(String tags) {
    return tags != null ? tags.split(",") : null;
  }

  @Named("arrayToCsv")
  static String arrayToCsv(String[] tags) {
    return tags != null ? String.join(",", tags) : null;
  }
}
