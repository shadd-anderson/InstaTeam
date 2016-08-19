package com.InstaTeam.Instant.service;

import com.InstaTeam.Instant.model.Collaborator;

import java.util.List;

public interface CollaboratorService {
  List<Collaborator> findAll();
  Collaborator findById(Long id);
  void save(Collaborator collaborator);
  void delete(Collaborator collaborator);
}
