package com.task_pay.task_pay.models.enums;
import lombok.*;

@RequiredArgsConstructor
public enum Permission {

    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_CREATE("user:create"),
    USER_DELETE("user:delete"),
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    BANKER_READ("management:read");

    @Getter
    private final String permission;
}
