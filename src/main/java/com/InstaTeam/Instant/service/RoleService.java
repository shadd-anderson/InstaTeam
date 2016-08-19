package com.InstaTeam.Instant.service;

import com.InstaTeam.Instant.model.Role;

public interface RoleService extends GenericService<Role> {
  Role findByName(String name);
}
