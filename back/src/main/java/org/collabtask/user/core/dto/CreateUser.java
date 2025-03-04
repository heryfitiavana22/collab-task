package org.collabtask.user.core.dto;

public class CreateUser {
    private String username;
    private String email;

    public CreateUser(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CreateUser{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
