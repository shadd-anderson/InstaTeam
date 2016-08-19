package com.InstaTeam.Instant.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

@Repository
public abstract class GenericDAOImpl<T> implements GenericDAO<T> {
  @Autowired
  SessionFactory sessionFactory;

  @Override
  public List<T> findAll(Class<T> classyClass) {
    Session session = sessionFactory.openSession();
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<T> criteria = builder.createQuery(classyClass);
    criteria.from(classyClass);
    List<T> entities = session.createQuery(criteria).getResultList();
    session.close();
    return entities;
  }

  @Override
  public T findById(Long id, Class<T> classyClass) {
    Session session = sessionFactory.openSession();
    T entity = session.find(classyClass, id);
    session.close();
    return entity;
  }

  @Override
  public void save(T type){
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.saveOrUpdate(type);
    session.getTransaction().commit();
    session.close();
  }

  @Override
  public void delete(T type) {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.delete(type);
    session.getTransaction().commit();
    session.close();
  }
}
