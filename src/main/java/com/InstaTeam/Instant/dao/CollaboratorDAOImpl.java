package com.InstaTeam.Instant.dao;

import com.InstaTeam.Instant.model.Collaborator;
import com.InstaTeam.Instant.model.Project;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

@Repository
public class CollaboratorDAOImpl implements CollaboratorDAO {
  @Autowired
  SessionFactory sessionFactory;

  @Override
  public List<Collaborator> findAll() {
    Session session = sessionFactory.openSession();
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<Collaborator> criteria = builder.createQuery(Collaborator.class);
    criteria.from(Collaborator.class);
    List<Collaborator> collaborators = session.createQuery(criteria).getResultList();
    session.close();
    return collaborators;
  }

  @Override
  public Collaborator findById(Long id) {
    Session session = sessionFactory.openSession();
    Collaborator collaborator = session.find(Collaborator.class, id);
    session.close();
    return collaborator;
  }

  @Override
  public void save(Collaborator collaborator) {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.saveOrUpdate(collaborator);
    session.getTransaction().commit();
    session.close();
  }

  @Override
  public void delete(Collaborator collaborator) {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.delete(collaborator);
    session.getTransaction().commit();
    session.close();
  }
}
