package com.liceman.application.usuario.infrastructure.dto;

import com.liceman.application.usuario.domain.enums.Area;
import com.liceman.application.usuario.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
    private Area area;
    private String phone;
    private Boolean hasTeams;

}
