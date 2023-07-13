package com.liceman.application.shared.application.mappers;

import com.liceman.application.training.domain.Training;
import com.liceman.application.training.infrastructure.dto.TrainingDTO;
import com.liceman.application.user.domain.User;
import com.liceman.application.user.infrastructure.dto.UserResponseDTO;
import com.liceman.application.user.infrastructure.dto.UserResponseWithoutTrainingDTO;

public interface MapperUtils {

    UserResponseDTO MapperToUserDTO (User user);

    UserResponseWithoutTrainingDTO MapperToUserWithoutTrainingDTO (User user);

    TrainingDTO mapperToTrainingUserResponseDTO (Training training);

    
}
