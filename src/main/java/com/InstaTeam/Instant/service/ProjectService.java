package com.InstaTeam.Instant.service;

import com.InstaTeam.Instant.model.Project;

import java.util.List;

public interface ProjectService {
  List<Project> findAll();
  Project findById(Long id);
  void save(Project project);
  void delete(Project project);
}
