package com.paulmount.paulfoliodisplay.services;/* Created by paulm on 2/6/2024*/

import com.paulmount.paulfoliodisplay.model.Project;
import com.paulmount.paulfoliodisplay.model.ProjectPagedList;
import com.paulmount.paulfoliodisplay.model.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ProjectClientService {


    @GetExchange("/project")
    public ResponseEntity<ProjectPagedList> getProjects(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                        @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                        @RequestParam(value = "projectName", required = false) String projectName,
                                                        @RequestParam(value = "url", required = false) String url,
                                                        @RequestParam(value = "tags", required = false) String tags);

    @GetExchange("/project/{id}")
    public ResponseEntity<Optional<Project>> getProjectBytId(@PathVariable UUID id);

    @PostExchange("/project")
    public ResponseEntity<Project> createProject(@RequestHeader Map<String, String> header, Project project);

    @PutExchange("/project/{id}")
    public void updateProject(@RequestHeader Map<String, String> header, @PathVariable UUID id, Project project);

    @DeleteExchange("/project/{id}")
    public void deleteProject(@RequestHeader Map<String, String> header, @PathVariable UUID id);


    @GetExchange("/tags")
    public ResponseEntity<List<Tag>> getTags();

    @GetExchange("/tags/{tagName}")
    public ResponseEntity<Tag> getTagCount(@PathVariable String tagName);
}
