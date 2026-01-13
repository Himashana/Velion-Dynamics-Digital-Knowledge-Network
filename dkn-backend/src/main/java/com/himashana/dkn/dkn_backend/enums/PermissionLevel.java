package com.himashana.dkn.dkn_backend.enums;

import java.util.Arrays;

public enum PermissionLevel {
    LEADERSHIP(1), // Usually the admin level
    KNOWLADGE_CHAMPION(2),
    // Levels: 3, 4 reserved for future use
    SENIOR_CONSULTANT(5),
    JUNIOR_CONSULTANT(6);

    private final int level;

    PermissionLevel(int level) {
        this.level = level;
    }

    public static PermissionLevel fromLevel(int level) {
        return Arrays.stream(values())
                .filter(p -> p.level == level)
                .findFirst()
                .orElse(JUNIOR_CONSULTANT);
    }

    public String asRole() {
        return "ROLE_" + name();
    }
}
