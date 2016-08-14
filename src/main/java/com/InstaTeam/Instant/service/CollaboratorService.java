package com.InstaTeam.Instant.service;

import com.InstaTeam.Instant.model.Collaborator;

import java.util.List;

public interface CollaboratorService {
  List<Collaborator> findAll();
  void save(Collaborator collaborator);
}
