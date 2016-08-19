package com.InstaTeam.Instant.Controller;

import com.InstaTeam.Instant.FlashMessage;
import com.InstaTeam.Instant.FlashMessage.Status;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;

@Controller
public class RoleController {
  @Autowired
  private RoleService roleService;

  @Autowired
  private ProjectService projectService;

  @Autowired
  private CollaboratorService collaboratorService;

  @RequestMapping("/roles")
  public String roles(Model model) {
    model.addAttribute("project", null);
    model.addAttribute("roles", roleService.findAll());
    if (!model.containsAttribute("role")) {
      model.addAttribute("role", new Role());
    }
    return "roles";
  }

  @RequestMapping(value = "/roles/add", method = RequestMethod.POST)
  public String addRole(@Valid Role role, BindingResult result, RedirectAttributes attributes) {
    if (result.hasErrors()) {
      attributes.addFlashAttribute("role", role);
      attributes.addFlashAttribute("org.springframework.validation.BindingResult.role", result);
      return "redirect:/roles";
    }
    if (roleService.findAll().stream().filter(rolee -> rolee.getName().equals(role.getName()))
        .findAny().isPresent()) {
      FlashMessage
          message =
          new FlashMessage(String.format("Role with name \'%s\' already exists", role.getName()),
              Status.FAILURE);
      attributes.addFlashAttribute("flash", message);
      return "redirect:/roles";
    }
    roleService.save(role);
    return "redirect:/roles";
  }

  @SuppressWarnings({"unchecked", "ResultOfMethodCallIgnored"})
  @RequestMapping(value = "/roles/{roleId}/delete", method = RequestMethod.POST)
  public String deleteRole(@PathVariable Long roleId, RedirectAttributes attributes) {
    Role role = roleService.findById(roleId);
    Map<Project, List<Collaborator>> changed = new HashMap<>();
    if (projectService.findAll().stream().filter(project -> project.getRolesNeeded().contains(role))
        .findAny().isPresent()) {
      List<Collaborator> allChanged;
      List<Project>
          conflicting =
          projectService.findAll().stream()
              .filter(project -> project.getRolesNeeded().contains(role)).collect(
              Collectors.toList());
      if (collaboratorService.findAll().stream()
          .filter(collaborator -> collaborator.getRole().equals(role)).findAny().isPresent()) {
        allChanged =
            collaboratorService.findAll().stream()
                .filter(collaborator -> collaborator.getRole().equals(role)).collect(
                Collectors.toList());
        for (Project project : conflicting) {
          List<Collaborator> projectChanged =
              allChanged.stream()
                  .filter(collaborator -> project.getCollaborators().contains(collaborator))
                  .collect(Collectors.toList());
          changed.put(project, projectChanged);
        }
        for (Collaborator collaborator : allChanged) {
          for (Project project : conflicting) {
            if (project.getCollaborators().contains(collaborator)) {
              if (project.getRolesNeeded().get(0).equals(role)) {
                if (project.getRolesNeeded().size() > 1) {
                  collaborator.setRole(project.getRolesNeeded().get(1));
                } else {
                  attributes.addFlashAttribute("flash", new FlashMessage(String
                      .format("The project \"%s\" only requires a %s. Please unassign this role before deleting it.",
                          project.getName(), role.getName()), Status.FAILURE));
                  return "redirect:/roles";
                }
              } else {
                collaborator.setRole(project.getRolesNeeded().get(0));
              }
            }
          }
          collaboratorService.save(collaborator);
        }
      }
      for (Project project : conflicting) {
        project.removeRole(role);
        projectService.save(project);
      }
    }
    roleService.delete(role);
    String changedMessage = "";
    if (!changed.entrySet().isEmpty()) {
      changedMessage += String.format("<p>The following have been affected with the deletion of %s:</p>",role.getName());
      for (Map.Entry entry : changed.entrySet()) {
        changedMessage += "<p>Project: <a href=\"/projects/" + ((Project)entry.getKey()).getId() + "/collaborators\">" + entry.getKey().toString() + "</a> with Collaborators: ";
        for (Collaborator collaborator : (List<Collaborator>) entry.getValue()) {
          changedMessage += collaborator.getName();
          if (((List<Collaborator>) entry.getValue()).indexOf(collaborator)
              != (((List<Collaborator>) entry.getValue()).size()) - 1) {
            changedMessage += ", ";
          } else {
            changedMessage += ". </p>";
          }
        }
      }
      changedMessage += "<p>Please check these to make sure they've remained accurate.</p>";
    }
    attributes.addFlashAttribute("flash", new FlashMessage(String
        .format(
            "<p>%s deleted successfully!</p>%s", role.getName(),
            changedMessage), Status.SUCCESS));
    return "redirect:/roles";
  }
}

