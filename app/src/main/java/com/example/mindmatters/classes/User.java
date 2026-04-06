package com.example.mindmatters.classes;

import java.io.Serializable;

/**
 * Base user model shared by all roles in the application.
 * Outstanding issues: role-specific behavior is still split between subclasses and screen controllers.
 */
public class User implements Serializable {
    private String userId;
    private String name;
    private String email;
    private String type;

    // Required empty constructor for Firestore object mapping.
    public User() {
    }

    // Creates a base user with shared identity fields.
    public User(String userId, String name, String email, String type) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.type = type;
    }

    // Returns the stored shared user fields.
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

    // Updates the stored shared user fields.
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
