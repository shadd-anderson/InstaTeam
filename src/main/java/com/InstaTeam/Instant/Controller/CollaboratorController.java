package com.InstaTeam.Instant.Controller;

import com.InstaTeam.Instant.model.Collaborator;
import com.InstaTeam.Instant.model.Project;
import com.InstaTeam.Instant.model.Role;
import com.InstaTeam.Instant.model.Wrapper;
import com.InstaTeam.Instant.service.CollaboratorService;
import com.InstaTeam.Instant.service.ProjectService;
import com.InstaTeam.Instant.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import javax.validation.Valid;

@Controller
public class CollaboratorController {
  @Autowired
  ProjectService projectService;

  @Autowired
  CollaboratorService collaboratorService;

  @Autowired
  RoleService roleService;

  @RequestMapping("/collaborators")
  public String collaborators(Model model) {
    model.addAttribute("collaborators", collaboratorService.findAll());
    model.addAttribute("project", null);
    model.addAttribute("roles", roleService.findAll());
    model.addAttribute("action", "/collaborators/add");
    model.addAttribute("collaborator", new Collaborator());
    return "collaborators";
  }

  @RequestMapping("/projects/{projectId}/collaborators")
  public String editCollaborators(Model model, @PathVariable Long projectId) {
    Project project = projectService.findById(projectId);
    List<Collaborator> collaboratorList = project.getCollaborators();
    Wrapper<Collaborator> collaborators = new Wrapper<>(collaboratorList);
    model.addAttribute("collaborators", collaborators);
    model.addAttribute("project", project);
    model.addAttribute("roles", project.getRolesNeeded());
    model.addAttribute("action", String.format("/projects/%s/collaborators/add",projectId));
    model.addAttribute("collaborator", new Collaborator());
    return "project_collaborators";
  }

  @RequestMapping(value = "/projects/{projectId}/collaborators/add", method = RequestMethod.POST)
  public String addCollaboratorToProject(Model model, @PathVariable Long projectId, @Valid Collaborator collaborator, BindingResult result) {
    Project project = projectService.findById(projectId);
    Role role = roleService.findById(collaborator.getRole().getId());
    collaborator.setRole(role);
    collaboratorService.save(collaborator);
    project.addCollaborator(collaborator);
    projectService.save(project);
    return String.format("redirect:/projects/%s/collaborators",projectId);
  }

  //A comment again
  @RequestMapping(value = "/projects/{projectId}/collaborators/update", method = RequestMethod.POST)
  public String updateProjectCollaborators(@PathVariable Long projectId, Wrapper<Collaborator> collaborators) {
    Project project = projectService.findById(projectId);
    for(Collaborator collaborator: collaborators.getWrappedList()) {
      Role role = roleService.findById(collaborator.getRole().getId());
      collaborator.setRole(role);
      collaboratorService.save(collaborator);
    }
    project.setCollaborators(collaborators.getWrappedList());
    projectService.save(project);
    return String.format("redirect:/projects/%s",projectId);
  }
}
