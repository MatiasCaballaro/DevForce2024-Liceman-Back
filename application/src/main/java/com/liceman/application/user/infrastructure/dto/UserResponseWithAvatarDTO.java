package com.liceman.application.user.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseWithAvatarDTO {

        private Long id;
        private String firstname;
        private String lastname;
        private String email;
        private String role;
        private String phone;
        private Boolean hasTeams;
        private String avatar;
}
