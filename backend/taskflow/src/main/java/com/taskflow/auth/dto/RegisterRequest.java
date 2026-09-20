package com.taskflow.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class RegisterRequest {
    @NotBlank (message = "Name cannot be blank")
    private String name;

    private String username;

    @NotBlank (message = "Email cannot be blank")
    @Email (message = "The format of the email is invalid")
    private String email;
    @NotBlank (message = "Password cannot be blank")
    @Size (min = 6, message = "Password must be at least 6 characters long")
    private String password;
}
