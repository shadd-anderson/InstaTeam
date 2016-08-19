package com.InstaTeam.Instant.dao;

import com.InstaTeam.Instant.model.Role;

import java.util.List;

public interface RoleDAO extends GenericDAO<Role> {
  Role findByName(String name);
}
