package com.example.miniatures.model.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ROLE_ADMIN("Admin role, can control everything"),
    ROLE_CLIENT("Client role, can control some functionalities");

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

}
