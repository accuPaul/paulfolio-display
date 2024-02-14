package com.paulmount.paulfoliodisplay.controller;

import com.paulmount.paulfoliodisplay.model.AppUser;
import com.paulmount.paulfoliodisplay.services.GithubRepoService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * *  Created by paulm on 2/14/2024
 */

@Controller
public class RepoController {


    GithubRepoService githubRepoService() {
        WebClient githubClient = WebClient.builder()
                .baseUrl("https://api.github.com/users")
                .build();
        HttpServiceProxyFactory ghFactory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(githubClient)).build();
        return ghFactory.createClient(GithubRepoService.class);
    }

    @GetMapping("/repos")
    public String getRepos(@AuthenticationPrincipal AppUser appUser, Model model) {
        model.addAttribute("repos",
                githubRepoService().getRepos(appUser.getUsername())
                        .stream());
        return "repos";
    }
}
