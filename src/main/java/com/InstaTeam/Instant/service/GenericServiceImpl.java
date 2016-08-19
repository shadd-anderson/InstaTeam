package com.InstaTeam.Instant.service;

import com.InstaTeam.Instant.dao.GenericDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Service
public abstract class GenericServiceImpl<T> implements GenericService<T> {
  @Autowired
  GenericDAO<T> genericDAO;

  private Class<T> classy;

  @SuppressWarnings("unchecked")
  GenericServiceImpl() {
    this.classy = (Class<T>) ((ParameterizedType) getClass()
        .getGenericSuperclass()).getActualTypeArguments()[0];
  }

  @Override
  public List<T> findAll() {
    return genericDAO.findAll(classy);
  }


  @Override
  public T findById(Long id) {
    return (T) genericDAO.findById(id, classy);
  }

  @Override
  public void save(T type) {
    genericDAO.save(type);
  }

  @Override
  public void delete(T type) {
    genericDAO.delete(type);
  }
}
