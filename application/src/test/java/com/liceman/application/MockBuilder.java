package com.liceman.application;


import com.liceman.application.training.domain.enums.Status;
import com.liceman.application.training.infrastructure.dto.UpdateTrainingByAdminDTO;
import com.liceman.application.user.domain.enums.Area;
import com.liceman.application.user.domain.enums.Role;
import com.liceman.application.user.infrastructure.dto.UserRequestDTO;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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


}
