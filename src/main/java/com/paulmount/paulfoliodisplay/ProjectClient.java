package com.paulmount.paulfoliodisplay;/* Created by paulm on 2/6/2024*/

import com.paulmount.paulfoliodisplay.model.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectClient {

    @GetExchange("/")
    public ResponseEntity<List<Project>> getProjects(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                        @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                        @RequestParam(value = "projectName", required = false) String projectName,
                                        @RequestParam(value = "url", required = false) String url,
                                        @RequestParam(value = "tags", required = false) String tags);

    @GetExchange("/{id}")
    public ResponseEntity<Optional<Project>> getProjectBytId(@PathVariable UUID id);

    @PostExchange("/")
    public ResponseEntity<Project> createProject(Project project);

    @PutExchange("/{id}")
    public void updateProject(@PathVariable UUID id, Project project);

    @DeleteExchange("/{id}")
    public void deleteProject(@PathVariable UUID id);
}
