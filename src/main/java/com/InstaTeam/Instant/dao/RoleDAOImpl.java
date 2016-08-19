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
public class RoleDAOImpl extends GenericDAOImpl<Role> implements RoleDAO {
  @Autowired
  private SessionFactory sessionFactory;

  @Override
  public Role findByName(String name) {
    Session session = sessionFactory.openSession();
    Query query = session.createQuery("from Role where name=:name");
    query.setParameter("name", name);
    return (Role) query.uniqueResult();
  }
}
