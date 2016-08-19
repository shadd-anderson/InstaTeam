package com.InstaTeam.Instant.Controller;

import static com.InstaTeam.Instant.model.Project.projectComparator;

import com.InstaTeam.Instant.FlashMessage;
import com.InstaTeam.Instant.model.Collaborator;
import com.InstaTeam.Instant.model.Project;
import com.InstaTeam.Instant.model.Role;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;

@Controller
public class ProjectController {
  @Autowired
  private ProjectService projectService;

  @Autowired
  private RoleService roleService;

  @Autowired
  private CollaboratorService collaboratorService;

  @RequestMapping("/")
  public String projectIndex(Model model) {
    List<Project> projects = new ArrayList<>(projectService.findAll());
    projects.sort(projectComparator);
    model.addAttribute("projects", projects);
    return "index";
  }


  @RequestMapping(value = "/new-project", method = RequestMethod.POST)
  public String saveNewProject(Model model, @Valid Project project, BindingResult result,
                               RedirectAttributes attributes) {
    if (result.hasErrors()) {
      attributes.addFlashAttribute("project", project);
      attributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);
      return "redirect:/new-project";
    }
    List<Role> roles = new ArrayList<>();
    if (project.getRolesNeeded() != null) {
      for (Role role : project.getRolesNeeded()) {
        if (role.getName() != null) {
          roles.add(roleService.findByName(role.getName()));
        }
      }
      project.setRolesNeeded(roles);
    } else {
      project.setRolesNeeded(new ArrayList<>());
    }
    project.setDateCreated(Date.from(Instant.from(ZonedDateTime.now())));
    projectService.save(project);
    return String.format("redirect:/projects/%d", project.getId());
  }

  @RequestMapping("/new-project")
  public String newProject(Model model, RedirectAttributes attributes) {
    if(roleService.findAll().isEmpty()) {
      attributes.addFlashAttribute("flash", new FlashMessage("Projects cannot be created without assigning a role. Please create at least one before adding a project.",
          FlashMessage.Status.FAILURE));
      return "redirect:/roles";
    }
    model.addAttribute("roles", roleService.findAll());
    model.addAttribute("action", "/new-project");
    model.addAttribute("editOrNew", "New Project");
    if (!model.containsAttribute("project")) {
      Project project = new Project();
      project.setRolesNeeded(new ArrayList<>());
      model.addAttribute("project", project);
    } else {
      Project project = ((Project) model.asMap().get("project"));
      if (project.getRolesNeeded() == null) {
        project.setRolesNeeded(new ArrayList<>());
        model.addAttribute("project", project);
      }
    }
    model.addAttribute("redirect", "/");
    return "edit_project";
  }

  @RequestMapping("/projects/{projectId}")
  public String projectDetail(@PathVariable Long projectId, Model model) {
    Project project = projectService.findById(projectId);
    model.addAttribute("project", project);
    model.addAttribute("collaborators", project.getCollaborators());
    return "project_detail";
  }

  @RequestMapping("/projects/{projectId}/edit")
  public String projectEdit(@PathVariable Long projectId, Model model) {
    model.addAttribute("roles", roleService.findAll());
    if(!model.containsAttribute("project")) {
      Project project = projectService.findById(projectId);
      model.addAttribute("project", project);
      model.addAttribute("collaborators", project.getCollaborators());
      model.addAttribute("editOrNew", String.format("Edit %s",project.getName()));
    }
    model.addAttribute("action", String.format("/projects/%s/edit", projectId));
    model.addAttribute("redirect", String.format("/projects/%s", projectId));
    return "edit_project";
  }

  @RequestMapping(value = "/projects/{projectId}/edit", method = RequestMethod.POST)
  public String submitProjectEdit(@Valid Project project, BindingResult result, Model model,
                                  RedirectAttributes attributes) {
    if (result.hasErrors()) {
      attributes.addFlashAttribute("project", project);
      attributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);
      attributes.addFlashAttribute("collaborators", project.getCollaborators());
      attributes.addFlashAttribute("editOrNew", String.format("Edit %s",project.getName()));
      return String.format("redirect:/projects/%s/edit", project.getId());
    }
    List<Role> newRoles = project.getRolesNeeded() != null ?
        project.getRolesNeeded().stream().map(role -> roleService.findByName(role.getName()))
            .collect(Collectors.toList()) : new ArrayList<>();
    if(project.getRolesNeeded() != null) {
      project.setRolesNeeded(project.getRolesNeeded().stream()
          .filter(role -> role.getName() != null).collect(Collectors.toList()));
      project.setRolesNeeded(newRoles);
    }
    projectService.save(project);
    if (project.getCollaborators().stream()
        .filter(collaborator -> !newRoles
            .contains(collaboratorService.findById(collaborator.getId()).getRole())).findFirst().isPresent()) {
      attributes.addFlashAttribute("flash",
          new FlashMessage("You have removed a role previously assigned to a Collaborator. "
              + "Please re-assign collaborator roles", FlashMessage.Status.FAILURE));
      return String.format("redirect:/projects/%s/collaborators", project.getId());
    }
    return String.format("redirect:/projects/%s", project.getId());
  }

  @RequestMapping(value = "/projects/{projectId}/delete", method = RequestMethod.POST)
  public String deleteProject(@PathVariable Long projectId) {
    Project project = projectService.findById(projectId);
    List<Collaborator> collaborators = new ArrayList<>(project.getCollaborators());
    for(Collaborator collaborator: collaborators) {
      project.removeCollaborator(collaborator);
    }
    projectService.save(project);
    for(Collaborator collaborator: collaborators) {
      collaborator.setRole(null);
      collaboratorService.delete(collaborator);
    }
    projectService.delete(project);
    return "redirect:/";
  }
}
