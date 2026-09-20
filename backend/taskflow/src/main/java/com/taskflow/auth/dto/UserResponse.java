package com.taskflow.auth.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private long id;
    private String name;
    private String surname;
    private String email;
    private String role;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
