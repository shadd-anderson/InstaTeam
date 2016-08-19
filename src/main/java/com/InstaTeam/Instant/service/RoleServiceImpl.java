package com.InstaTeam.Instant.service;

import com.InstaTeam.Instant.dao.GenericDAO;
import com.InstaTeam.Instant.dao.RoleDAO;
import com.InstaTeam.Instant.model.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends GenericServiceImpl<Role> implements RoleService {
  private RoleDAO roleDAO;

  public RoleServiceImpl(){}

  @Autowired
  public RoleServiceImpl(@Qualifier("roleDAOImpl")GenericDAO<Role> genericDAO) {
    super(genericDAO);
    this.roleDAO = (RoleDAO) genericDAO;
  }

  @Override
  public Role findByName(String name) {
    return roleDAO.findByName(name);
  }
}
