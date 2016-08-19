package com.InstaTeam.Instant.service;

import com.InstaTeam.Instant.dao.RoleDAO;
import com.InstaTeam.Instant.model.Role;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends GenericServiceImpl<Role> implements RoleService {
  @Autowired
  RoleDAO roleDAO;

  @Override
  public Role findByName(String name) {
    return roleDAO.findByName(name);
  }
}
