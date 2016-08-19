package com.InstaTeam.Instant.service;

import com.InstaTeam.Instant.dao.GenericDAO;
import com.InstaTeam.Instant.dao.ProjectDAO;
import com.InstaTeam.Instant.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl extends GenericServiceImpl<Project> implements ProjectService {
  private ProjectDAO projectDAO;

  public ProjectServiceImpl(){}

  @Autowired
  public ProjectServiceImpl(@Qualifier("projectDAOImpl")GenericDAO<Project> genericDAO) {
    super(genericDAO);
    this.projectDAO = (ProjectDAO) genericDAO;
  }
}
