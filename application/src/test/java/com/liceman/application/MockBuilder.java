package com.liceman.application;

import com.liceman.application.training.domain.enums.Status;
import com.liceman.application.training.infrastructure.dto.UpdateTrainingByAdminDTO;
import com.liceman.application.user.domain.enums.Area;
import com.liceman.application.user.domain.enums.Role;
import com.liceman.application.user.infrastructure.dto.UserRequestDTO;

public class MockBuilder {

    public static UserRequestDTO buildUserRequestDTO(Role role, Area area){
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setRole(role);
        userRequestDTO.setArea(area);

        return userRequestDTO;
    }

    public static UpdateTrainingByAdminDTO buildUpdateTrainingByAdminDTO(Status status)
    {
        UpdateTrainingByAdminDTO updateTrainingByAdminDTO = new UpdateTrainingByAdminDTO();
        updateTrainingByAdminDTO.setStatus(status);
        return updateTrainingByAdminDTO;
    }


    public static UserRequestDTO buildUserOkPHResponseDefault(){
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setArea(null);
        userRequestDTO.setEmail("email@Test");
        userRequestDTO.setRole(Role.ADMIN);
        userRequestDTO.setPassword("passwordTest");
        userRequestDTO.setFirstname("firstNameTest");
        userRequestDTO.setLastname("lastNameTest");
        userRequestDTO.setPhone("phoneTest");
        userRequestDTO.setHasTeams(true);

        return userRequestDTO;
    }
}
