package com.InstaTeam.Instant.Controller;

import com.InstaTeam.Instant.model.Project;
import com.InstaTeam.Instant.model.Role;
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

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

@Controller
public class ProjectController {
  @Autowired
  private ProjectService projectService;

  @Autowired
  private RoleService roleService;

  @RequestMapping("/")
  public String projectIndex(Model model) {
    model.addAttribute("projects",projectService.findAll());
    return "index";
  }

  @RequestMapping(value = "/new-project", method = RequestMethod.POST)
  public String saveNewProject(Model model, @Valid Project project, BindingResult result, RedirectAttributes attributes) {
    if(result.hasErrors()) {
      attributes.addFlashAttribute("project", project);
      attributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);
      return "redirect:/new-project";
    }
    projectService.save(project);
    return String.format("redirect:/projects/%d",project.getId());
  }

  @RequestMapping("/new-project")
  public String newProject(Model model) {
    model.addAttribute("roles", roleService.findAll());
    model.addAttribute("action", "/new-project");
    if(!model.containsAttribute("project")) {
      model.addAttribute("project", new Project());
    }
    model.addAttribute("redirect", "/");
    return "edit_project";
  }

  @RequestMapping("/projects/{projectId}")
  public String projectDetail(@PathVariable Long projectId,Model model) {
    model.addAttribute("project",projectService.findById(projectId));
    return "project_detail";
  }

  @RequestMapping("/projects/{projectId}/edit")
  public String projectEdit(@PathVariable Long projectId, Model model) {
    model.addAttribute("roles", roleService.findAll());
    model.addAttribute("project", projectService.findById(projectId));
    model.addAttribute("action", "/projects/{projectId}/edit");
    model.addAttribute("redirect", String.format("/projects/%s", projectId));
    return "edit_project";
  }

  @RequestMapping(value = "/projects/{projectId}/edit", method = RequestMethod.POST)
  public String submitProjectEdit(@Valid Project project, BindingResult result, Model model, RedirectAttributes attributes) {
    if(result.hasErrors()) {
      attributes.addFlashAttribute("project",project);
      return String.format("redirect:/projects/%s/edit",project.getId());
    }
    List<Role> roles = new ArrayList<>();
    for(Role role: project.getRolesNeeded()) {
      roles.add(roleService.findById(Long.parseLong(role.getName())));
    }
    project.setRolesNeeded(roles);
    projectService.save(project);
    return String.format("redirect:/projects/%s",project.getId());
  }

  @RequestMapping(value = "/projects/{projectId}/delete", method = RequestMethod.POST)
  public String deleteProject(@PathVariable Long projectId) {
    Project project = projectService.findById(projectId);
    projectService.delete(project);
    return "redirect:/";
  }
}
