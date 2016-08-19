package com.InstaTeam.Instant.dao;

import java.util.List;

public interface GenericDAO<T> {
  List<T> findAll(Class<T> classyClass);
  T findById(Long id, Class<T> classyClass);
  void save(T type);
  void delete(T type);
}
