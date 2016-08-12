package com.InstaTeam.Instant.Controller;

import com.InstaTeam.Instant.model.Project;
import com.InstaTeam.Instant.service.ProjectService;
import com.InstaTeam.Instant.service.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProjectController {
  @Autowired
  private ProjectService projectService;

  @RequestMapping("/")
  public String projectIndex(Model model) {
    model.addAttribute("projects",projectService.findAll());
    return "index";
  }

  @RequestMapping("/new-project")
  public String newProject(Model model) {
    model.addAttribute("project", new Project());
    return "edit_project";
  }

  @RequestMapping("/collaborators")
  public String collaborators(Model model) {
    return "collaborators";
  }

  @RequestMapping("/roles")
  public String roles() {
    return "roles";
  }
}
