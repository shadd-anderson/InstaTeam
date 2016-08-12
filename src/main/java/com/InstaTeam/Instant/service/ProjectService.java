package com.InstaTeam.Instant.service;

import com.InstaTeam.Instant.model.Project;

import java.util.List;

public interface ProjectService {
  List<Project> findAll();
  void save(Project project);
}
