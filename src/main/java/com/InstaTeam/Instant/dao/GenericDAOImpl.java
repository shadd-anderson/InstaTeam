package com.InstaTeam.Instant.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

@SuppressWarnings("unchecked")
@Repository
public abstract class GenericDAOImpl<T> implements GenericDAO<T> {
  @Autowired
  SessionFactory sessionFactory;

  protected Class<T> classy;

  public GenericDAOImpl() {
    this.classy = (Class<T>) ((ParameterizedType) getClass()
        .getGenericSuperclass()).getActualTypeArguments()[0];
  }

  @Override
  public List<T> findAll() {
    Session session = sessionFactory.openSession();
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<T> criteria = builder.createQuery(classy);
    criteria.from(classy);
    List<T> entities = session.createQuery(criteria).getResultList();
    session.close();
    return entities;
  }

  @Override
  public T findById(Long id) {
    Session session = sessionFactory.openSession();
    T entity = session.find(classy, id);
    session.close();
    return entity;
  }

  @Override
  public void save(T type) {
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
