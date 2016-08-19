package com.InstaTeam.Instant.service;

import com.InstaTeam.Instant.dao.RoleDAO;
import com.InstaTeam.Instant.model.Role;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
  @Autowired
  RoleDAO roleDAO;

  @Autowired
  SessionFactory sessionFactory;

  @Override
  public List<Role> findAll() {
    return roleDAO.findAll();
  }

  @Override
  public Role findById(Long id) {
    return roleDAO.findById(id);
  }

  @Override
  public Role findByName(String name) {
    return roleDAO.findByName(name);
  }

  @Override
  public void save(Role role) {
    roleDAO.save(role);
  }

  @Override
  public void delete(Role role) {
    roleDAO.delete(role);
  }
}
