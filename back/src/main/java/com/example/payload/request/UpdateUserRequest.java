package com.example.payload.request;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;

public class UpdateUserRequest {
    
    @NotBlank @Size(min = 2, max = 40)
    private String firstname;

    @NotBlank @Size(min = 2, max = 40)
    private String lastname;

    @Size(min = 8)
    private String password;

    public UpdateUserRequest() {}

    public UpdateUserRequest(String firstname, String lastname, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
    }

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
