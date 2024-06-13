package com.task_pay.task_pay.models.enums;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static com.task_pay.task_pay.models.enums.Permission.*;


@Getter
@RequiredArgsConstructor
public enum Role {
    // user has all permissions
    USER(
            Set.of(
                    USER_READ,
                    USER_UPDATE,
                    USER_DELETE,
                    USER_CREATE
            )
    ),

    // admin has all permissions
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE

            )
    ),
//    Banker has only read permission
    BANKER(
            Set.of(
                    BANKER_READ,
                    BANKER_UPDATE,
                    BANKER_DELETE,
                    BANKER_CREATE
            )
    );



    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }


}
