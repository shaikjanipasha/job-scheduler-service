package com.vtx.jobscheduler.enums;

public enum RoleEnum {
    USER("USER", "User role"),
    GUEST("GUEST", "Guest role"),
    MODERATOR("MODERATOR", "Moderator role"),
    ADMIN("ADMIN", "Admin role");

    private final String value;
    private final String description;

    RoleEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public static RoleEnum fromValue(String value) {
        for (RoleEnum role : RoleEnum.values()) {
            if (role.value.equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + value);
    }
}
