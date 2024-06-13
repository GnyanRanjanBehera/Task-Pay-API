package com.task_pay.task_pay.models.enums;
import lombok.*;

import java.util.Collections;


@Getter
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

    BANKER_READ("banker:read"),
    BANKER_UPDATE("banker:update"),
    BANKER_CREATE("banker:create"),
    BANKER_DELETE("banker:delete");


    private final String permission;
}
