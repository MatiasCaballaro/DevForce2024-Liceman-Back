package com.liceman.application.service;

import com.liceman.application.user.infrastructure.dto.UserRequestDTO;

public class MockBuilder {
    //public static BinPHResponse buildBinPHResponseDefault()
    public static UserRequestDTO buildUserRequestDTODefault(){
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setFirstname("Manuel");
        userRequestDTO.setLastname("Lopez");
        userRequestDTO.setPassword("123");
        userRequestDTO.setPhone("49033456");
        userRequestDTO.setHasTeams(true);
        return userRequestDTO;
    }
}
