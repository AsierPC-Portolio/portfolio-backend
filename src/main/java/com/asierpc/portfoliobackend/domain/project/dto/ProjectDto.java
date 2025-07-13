package com.asierpc.portfoliobackend.domain.project.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Project.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDto {
  private Long id;
  private String name;
  private String description;
  private String countryCode;
  private String link;
  private String[] tags;
  private LocalDate startDate;
  private LocalDate endDate;
}
