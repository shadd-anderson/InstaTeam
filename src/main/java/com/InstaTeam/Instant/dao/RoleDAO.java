package com.InstaTeam.Instant.dao;

import com.InstaTeam.Instant.model.Role;

import java.util.List;

public interface RoleDAO {
  List<Role> findAll();
  Role findById(Long id);
  Role findByName(String name);
  void save(Role role);
  void delete(Role role);
}
