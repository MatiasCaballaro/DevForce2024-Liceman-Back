package com.liceman.application.user.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.liceman.application.user.domain.enums.Permission.*;

@RequiredArgsConstructor
public enum Role {

    USER(
            Set.of(
                    USER_READ,
                    USER_UPDATE,
                    USER_DELETE,
                    TRAINING_CREATE,
                    TRAINING_READ,
                    TRAINING_UPDATE

            )
    ),
    ADMIN(
            Set.of(
                    USER_READ,
                    USER_CREATE,
                    USER_UPDATE,
                    USER_DELETE,
                    MENTOR_READ,
                    MENTOR_UPDATE,
                    MENTOR_DELETE,
                    MENTOR_CREATE,
                    TRAINING_UPDATE,
                    TRAINING_READ,
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE
            )
    ),
    MENTOR(
            Set.of(
                    USER_READ,
                    MENTOR_READ,
                    MENTOR_UPDATE,
                    MENTOR_DELETE,
                    MENTOR_CREATE,
                    TRAINING_CREATE,
                    TRAINING_READ,
                    TRAINING_UPDATE
            )
    );

    @Getter
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