package com.paulmount.paulfoliodisplay.services;/* Created by paulm on 2/14/2024*/

import com.paulmount.paulfoliodisplay.model.GithubRepo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface GithubRepoService {

    @GetExchange("/{user}/repos")
    List<GithubRepo> getRepos(@PathVariable String user);
}
