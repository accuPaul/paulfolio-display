package com.paulmount.paulfoliodisplay.controller;

import com.paulmount.paulfoliodisplay.model.AppUser;
import com.paulmount.paulfoliodisplay.services.ProjectClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * *  Created by paulm on 2/6/2024
 */

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProjectClientService projectClient;

    @ModelAttribute
    AppUser appUser(@AuthenticationPrincipal AppUser appUser) {
        return appUser;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping
    public String home(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                       @RequestParam(value = "pageSize", required = false) Integer pageSize,
                       @RequestParam(value = "projectName", required = false) String projectName,
                       @RequestParam(value = "url", required = false) String url,
                       @RequestParam(value = "tags", required = false) String tags,
                       Model model) {

        if (pageNumber == null) pageNumber = 0;
        if (pageSize == null) pageSize = 20;

        model.addAttribute("projects", projectClient.getProjects(pageNumber, pageSize, projectName, url, tags).getBody());
        model.addAttribute("tags", projectClient.getTags().getBody());
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
