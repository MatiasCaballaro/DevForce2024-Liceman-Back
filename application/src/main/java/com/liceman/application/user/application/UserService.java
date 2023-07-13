package com.liceman.application.user.application;

import com.liceman.application.security.infrastructure.dto.AuthenticationResponse;
import com.liceman.application.user.infrastructure.dto.UserRequestDTO;
import com.liceman.application.user.infrastructure.dto.UserResponseDTO;
import com.liceman.application.user.infrastructure.dto.UserResponseWithoutTrainingDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    AuthenticationResponse createUser (UserRequestDTO userRequest);

    List<UserResponseDTO> findAllUsers ();

    Optional<UserResponseDTO> getUserById (Long id);

    UserResponseDTO updateOwnUser (UserRequestDTO userRequestDTO);

    void deleteUserbyId (Long id);

    Optional<UserResponseWithoutTrainingDTO> getLoggedUser();
}
