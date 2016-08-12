package com.InstaTeam.Instant.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String description;
  private Status status;

  @ManyToMany
  private Set<Role> rolesNeeded;

  @ManyToMany
  private Set<Collaborator> collaborators;

  public Project(){}

  public Project(String name, String description, Status status) {
    this.name=name;
    this.description=description;
    this.status=status;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Set<Role> getRolesNeeded() {
    return rolesNeeded;
  }

  public void setRolesNeeded(Set<Role> rolesNeeded) {
    this.rolesNeeded = rolesNeeded;
  }

  public Set<Collaborator> getCollaborators() {
    return collaborators;
  }

  public void setCollaborators(Set<Collaborator> collaborators) {
    this.collaborators = collaborators;
  }

  public enum Status {
    Active, Archived, Pending
  }
}
