package com.InstaTeam.Instant.dao;

import java.util.List;

public interface GenericDAO<T> {
  List<T> findAll();
  T findById(Long id);
  void save(T type);
  void delete(T type);
}
