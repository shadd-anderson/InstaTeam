package com.InstaTeam.Instant.service;

import com.InstaTeam.Instant.dao.CollaboratorDAO;
import com.InstaTeam.Instant.dao.GenericDAO;
import com.InstaTeam.Instant.model.Collaborator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CollaboratorServiceImpl extends GenericServiceImpl<Collaborator> implements CollaboratorService {
  private CollaboratorDAO collaboratorDAO;

  public CollaboratorServiceImpl(){}

  @Autowired
  public CollaboratorServiceImpl(@Qualifier("collaboratorDAOImpl")GenericDAO<Collaborator> genericDAO) {
    super(genericDAO);
    this.collaboratorDAO = (CollaboratorDAO) genericDAO;
  }
}
