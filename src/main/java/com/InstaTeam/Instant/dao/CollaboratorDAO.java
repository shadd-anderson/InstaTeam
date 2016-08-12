package com.InstaTeam.Instant.dao;

import com.InstaTeam.Instant.model.Collaborator;

import java.util.List;

public interface CollaboratorDAO {
  List<Collaborator> findAll();
  Collaborator findById(Long id);
  void save(Collaborator collaborator);
  void delete(Collaborator collaborator);
}
