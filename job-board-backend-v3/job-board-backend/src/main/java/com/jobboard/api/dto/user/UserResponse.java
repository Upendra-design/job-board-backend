package com.jobboard.api.dto.user;

import com.jobboard.api.model.Role;
import com.jobboard.api.model.User;

import java.time.LocalDateTime;

public class UserResponse {

    private Long id;
    private String fullName;
    private String email;
    private Role role;
    private LocalDateTime createdAt;

    public static UserResponse fromEntity(User user) {
        UserResponse dto = new UserResponse();
        dto.id = user.getId();
        dto.fullName = user.getFullName();
        dto.email = user.getEmail();
        dto.role = user.getRole();
        dto.createdAt = user.getCreatedAt();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
