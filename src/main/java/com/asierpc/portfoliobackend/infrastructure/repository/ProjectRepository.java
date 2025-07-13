package com.asierpc.portfoliobackend.infrastructure.repository;

import com.asierpc.portfoliobackend.domain.project.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Project entity with custom filtering by name and tag.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

  Page<Project> findByNameContainingIgnoreCaseAndTagsContainingIgnoreCase(String name, String tag, Pageable pageable);
}
