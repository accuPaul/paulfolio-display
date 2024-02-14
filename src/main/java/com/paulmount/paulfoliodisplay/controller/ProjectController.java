package com.paulmount.paulfoliodisplay.controller;

import com.paulmount.paulfoliodisplay.command.SearchCommand;
import com.paulmount.paulfoliodisplay.converters.SearchCommandToString;
import com.paulmount.paulfoliodisplay.model.AppUser;
import com.paulmount.paulfoliodisplay.model.Project;
import com.paulmount.paulfoliodisplay.services.ProjectClientService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;
import java.util.UUID;

/**
 * *  Created by paulm on 2/12/2024
 */

@Controller
public class ProjectController {
    private final ProjectClientService projectClient;
    private final SearchCommandToString searchCommandToString;



    public ProjectController(ProjectClientService projectClient, SearchCommandToString searchCommandToString) {
        this.projectClient = projectClient;
        this.searchCommandToString = searchCommandToString;
    }

    @GetMapping("/project/{id}/update")
    public String updateProject(@PathVariable String id, Model model, @AuthenticationPrincipal AppUser appUser) {
        if (appUser != null && appUser.getAdmin()) {
            UUID projectId = UUID.fromString(id);
            Project project = new Project(UUID.randomUUID(),0, null, null, null,
                    null,null,null, null);
            projectClient.updateProject(Map.of("Authorization","Bearer "+appUser.getAccessToken()),
                    projectId, project);
        }
        return "index";
    }

    @GetMapping("/project/new")
    public String saveNewProject(Model model, @AuthenticationPrincipal AppUser appUser) {
        if (appUser != null && appUser.getAdmin()) {
            Project project = new Project(UUID.randomUUID(),0, null, null, null,
                    null,null,null, null);
            projectClient.createProject(Map.of("Authorization","Bearer "+appUser.getAccessToken()), project);
        }
        return "index";
    }

    @GetMapping("/project/{id}/delete")
    public String deleteProjectById(@PathVariable String id, @AuthenticationPrincipal AppUser appUser) {
        UUID projectId = UUID.fromString(id);
        if (appUser != null && appUser.getAdmin()) projectClient.deleteProject(Map.of("Authorization","Bearer "+appUser.getAccessToken()), projectId);
        return "redirect:/";
    }

    @GetMapping("/project/tag/{tagName}")
    public String getProjectsByTag(@PathVariable String tagName, Model model) {
        model.addAttribute("projects", projectClient.getProjects(0, 20, null, null, tagName).getBody());
        return "index";
    }

    @PostMapping("/search")
    public String doSearch(@ModelAttribute SearchCommand command, Model model) {
        String searchString = searchCommandToString.convert(command);
        System.out.println("Search string = "+searchString);
        model.addAttribute("projects", projectClient.getProjects(0, 20, searchString, searchString, null).getBody());
        return "index";
    }

}
