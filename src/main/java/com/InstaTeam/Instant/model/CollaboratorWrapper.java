package com.InstaTeam.Instant.model;

import java.util.ArrayList;
import java.util.List;

public class CollaboratorWrapper {
  private List<Collaborator> wrappedList;

  public CollaboratorWrapper(){}

  public CollaboratorWrapper(List<Collaborator> wrappedList) {
    this.wrappedList = wrappedList;
  }

  public List<Collaborator> getWrappedList() {
    return wrappedList;
  }

  public void setWrappedList(
      List<Collaborator> wrappedList) {
    this.wrappedList = wrappedList;
  }
}
