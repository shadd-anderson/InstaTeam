package com.InstaTeam.Instant.model;

import java.util.ArrayList;
import java.util.List;

public class Wrapper<T> {
  private List<T> wrappedList = new ArrayList<>();

  public Wrapper(){}

  public Wrapper(List<T> wrappedList) {
    this.wrappedList = wrappedList;
  }

  public List<T> getWrappedList() {
    return wrappedList;
  }

  public void setWrappedList(
      List<T> wrappedList) {
    this.wrappedList = wrappedList;
  }
}
