package com.InstaTeam.Instant.service;

import com.InstaTeam.Instant.dao.ProjectDAO;
import com.InstaTeam.Instant.dao.ProjectDAOImpl;
import com.InstaTeam.Instant.model.Project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
  @Autowired
  ProjectDAO projectDAO;

  @Override
  public List<Project> findAll() {
    return projectDAO.findAll();
  }

  @Override
  public Project findById(Long id) {
    return projectDAO.findById(id);
  }

  @Override
  public void save(Project project) {
    projectDAO.save(project);
  }

  @Override
  public void delete(Project project) {
    projectDAO.delete(project);
  }
}
