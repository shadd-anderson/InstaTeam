package com.InstaTeam.Instant.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Size(min = 1, max = 25)
  private String name;

  @Size(max = 255)
  private String description;
  private Status status;

  @ManyToMany
  private List<Role> rolesNeeded;

  @ManyToMany
  private List<Collaborator> collaborators;

  public Project(){}

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

  public List<Role> getRolesNeeded() {
    return rolesNeeded;
  }

  public void setRolesNeeded(List<Role> rolesNeeded) {
    this.rolesNeeded = rolesNeeded;
  }

  public List<Collaborator> getCollaborators() {
    return collaborators;
  }

  public void setCollaborators(List<Collaborator> collaborators) {
    this.collaborators = collaborators;
  }

  public void addCollaborator(Collaborator collaborator) {
    collaborators.add(collaborator);
  }

  public enum Status {
    Active, Archived, Pending
  }
}
