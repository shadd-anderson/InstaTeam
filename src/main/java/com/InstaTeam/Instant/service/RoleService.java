package com.InstaTeam.Instant.service;

import com.InstaTeam.Instant.model.Role;

import java.util.List;

public interface RoleService {
  List<Role> findAll();
  Role findById(Long id);
  void save(Role role);
  void delete(Role role);
}
