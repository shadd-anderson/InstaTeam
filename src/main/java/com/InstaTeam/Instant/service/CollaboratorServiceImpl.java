package com.InstaTeam.Instant.service;

import com.InstaTeam.Instant.dao.CollaboratorDAO;
import com.InstaTeam.Instant.model.Collaborator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollaboratorServiceImpl implements CollaboratorService {
  @Autowired
  CollaboratorDAO collaboratorDAO;

  @Override
  public List<Collaborator> findAll() {
    return collaboratorDAO.findAll();
  }


  @Override
  public void save(Collaborator collaborator) {
    collaboratorDAO.save(collaborator);
  }

  @Override
  public Collaborator findById(Long id) {
    return collaboratorDAO.findById(id);
  }

  @Override
  public void delete(Collaborator collaborator) {
    collaboratorDAO.delete(collaborator);
  }
}
