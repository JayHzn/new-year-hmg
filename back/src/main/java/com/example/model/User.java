package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Document(collection = "users")
public class User {
    @Id
    private String id;

    @NotBlank(message = "Username")
    private String username;

    @Email
    private String email;

    @NotBlank
    private String password;
}
