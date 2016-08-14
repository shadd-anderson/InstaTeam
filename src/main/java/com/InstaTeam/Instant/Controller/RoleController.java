package com.InstaTeam.Instant.Controller;

import com.InstaTeam.Instant.model.Role;
import com.InstaTeam.Instant.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class RoleController {
  @Autowired
  RoleService roleService;

  @RequestMapping("/roles")
  public String roles(Model model) {
    model.addAttribute("project", null);
    model.addAttribute("roles", roleService.findAll());
    model.addAttribute("role", new Role());
    return "roles";
  }

  @RequestMapping(value = "/roles/add", method = RequestMethod.POST)
  public String addRole(@Valid Role role, BindingResult result) {
    roleService.save(role);
    return "redirect:/roles";
  }

  @RequestMapping(value = "/roles/{roleId}/delete", method = RequestMethod.POST)
  public String deleteRole(@PathVariable Long roleId) {
    Role role = roleService.findById(roleId);
    roleService.delete(role);
    return "redirect:/roles";
  }
}
