package com.InstaTeam.Instant.dao;

import com.InstaTeam.Instant.model.Project;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

@Repository
public class ProjectDAOImpl implements ProjectDAO {
  @Autowired
  private SessionFactory sessionFactory;

  @Override
  public List<Project> findAll() {
    Session session = sessionFactory.openSession();
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<Project> criteria = builder.createQuery(Project.class);
    criteria.from(Project.class);
    List<Project> projects = session.createQuery(criteria).getResultList();
    session.close();
    return projects;
  }

  @Override
  public Project findById(Long id) {
    return null;
  }

  @Override
  public void save(Project project) {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.save(project);
    session.getTransaction().commit();
    session.close();
  }

  @Override
  public void delete(Project project) {

  }
}
