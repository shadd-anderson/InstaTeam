package com.InstaTeam.Instant.dao;

import com.InstaTeam.Instant.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoleDAOImpl implements RoleDAO {
  @Autowired
  private SessionFactory sessionFactory;

  @Override
  public List<Role> findAll() {
    return null;
  }

  @Override
  public Role findById(Long id) {
    return null;
  }

  @Override
  public void save(Role object) {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.save(object);
    session.getTransaction().commit();
    session.close();
  }

  @Override
  public void delete(Role object) {

  }
}
