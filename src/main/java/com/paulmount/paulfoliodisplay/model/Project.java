package com.paulmount.paulfoliodisplay.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public record Project(UUID id, Integer version, Timestamp createdDate, Timestamp lastModifiedDate,
                      String projectName, String description, String projectUrl, String projectSource,
                      List<String> tags) {
}
