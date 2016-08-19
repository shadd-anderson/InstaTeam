package com.InstaTeam.Instant.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
  @Size(min = 1, max = 25, message = "Please enter the name of the project")
  private String name;

  @NotEmpty(message = "Please provide a description for the project")
  @Size(max = 255)
  private String description;
  private Status status;

  @NotNull(message = "Please select at least one role to assign to this project")
  @ManyToMany
  private List<Role> rolesNeeded = new ArrayList<>();

  @ManyToMany
  private List<Collaborator> collaborators = new ArrayList<>();

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM:SS.sss")
  private Date dateCreated;

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

  private Status getStatus() {
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

  private Date getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }

  public void addCollaborator(Collaborator collaborator) {
    collaborators.add(collaborator);
  }

  public void removeCollaborator(Collaborator collaborator) {collaborators.remove(collaborator);}

  public void removeRole(Role role) {rolesNeeded.remove(role);}

  public enum Status {
    Active, Pending, Archived
  }

  @Override
  public String toString() {
    return name;
  }

  public static Comparator<Project> projectComparator = (a1, a2) -> {
    if(a1.getStatus().equals(a2.getStatus())) {
      return a1.getDateCreated().compareTo(a2.getDateCreated());
    } else {
      return a1.getStatus().compareTo(a2.getStatus());
    }
  };
}
