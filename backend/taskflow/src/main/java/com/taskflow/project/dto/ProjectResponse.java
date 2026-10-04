package com.taskflow.project.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponse {
    private Long id;
    private String name;
    private String description;
    private String status = "INACTIVE";
    private Long ownerId;
    private List<Long> members;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    

}
