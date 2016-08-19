package com.InstaTeam.Instant.service;

import com.InstaTeam.Instant.model.Role;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface RoleService extends GenericService<Role> {
  Role findByName(String name);
}
