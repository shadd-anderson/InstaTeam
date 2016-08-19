package com.InstaTeam.Instant.dao;

import com.InstaTeam.Instant.model.Role;

public interface RoleDAO extends GenericDAO<Role> {
  Role findByName(String name);
}
