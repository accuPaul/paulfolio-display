package com.paulmount.paulfoliodisplay.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * *  Created by paulm on 2/12/2024
 */

public class ProjectPagedList extends PageImpl<Project> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ProjectPagedList(@JsonProperty("content") List<Project> content,
                            @JsonProperty("number") int number,
                            @JsonProperty("size") int size,
                            @JsonProperty("totalElements") Long totalElements,
                            @JsonProperty("pageable") JsonNode pageable,
                            @JsonProperty("last") boolean last,
                            @JsonProperty("totalPages") int totalPages,
                            @JsonProperty("sort") JsonNode sort,
                            @JsonProperty("first") boolean first,
                            @JsonProperty("numberOfElements") int numberOfElements) {
        super(content, PageRequest.of(number, size), totalElements);
    }

    public ProjectPagedList(List<Project> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public ProjectPagedList(List<Project> content) {
        super(content);
    }
}
