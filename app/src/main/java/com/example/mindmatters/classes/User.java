package com.example.mindmatters.classes;

import java.io.Serializable;

public class User implements Serializable {
    private String userId;
    private String name;
    private String email;
    private String type;

    public User() {
    }

    public User(String userId, String name, String email, String type) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(String type) {
        this.type = type;
    }
}
