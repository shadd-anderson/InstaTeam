package com.InstaTeam.Instant.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Collaborator {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;

  @NotNull
  @Size(min = 1, max = 30)
  private String name;

  @ManyToOne(cascade = CascadeType.ALL)
  private Role role;

  public Collaborator() {}

  public Collaborator(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Collaborator that = (Collaborator) o;

    if (id != null ? !id.equals(that.id) : that.id != null) {
      return false;
    }
    return name != null ? name.equals(that.name) : that.name == null;

  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }
}
