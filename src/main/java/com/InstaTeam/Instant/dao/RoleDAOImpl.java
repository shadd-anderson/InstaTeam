package com.InstaTeam.Instant.dao;

import com.InstaTeam.Instant.model.Project;
import com.InstaTeam.Instant.model.Role;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

@Repository
public class RoleDAOImpl implements RoleDAO {
  @Autowired
  private SessionFactory sessionFactory;

  @Override
  public List<Role> findAll() {
    Session session = sessionFactory.openSession();
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<Role> criteria = builder.createQuery(Role.class);
    criteria.from(Role.class);
    List<Role> roles = session.createQuery(criteria).getResultList();
    session.close();
    return roles;
  }

  @Override
  public Role findById(Long id) {
    Session session = sessionFactory.openSession();
    Role role = session.get(Role.class, id);
    session.close();
    return role;
  }

  @Override
  public Role findByName(String name) {
    Session session = sessionFactory.openSession();
    Query query = session.createQuery("from Role where name=:name");
    query.setParameter("name", name);
    return (Role) query.uniqueResult();
  }

  @Override
  public void save(Role role) {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.saveOrUpdate(role);
    session.getTransaction().commit();
    session.close();
  }

  @Override
  public void delete(Role role) {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.delete(role);
    session.getTransaction().commit();
    session.close();
  }
}
