package com.liceman.application.user.application;

import com.liceman.application.security.infrastructure.dto.AuthenticationResponse;
import com.liceman.application.user.infrastructure.dto.UserRequestDTO;
import com.liceman.application.user.infrastructure.dto.UserResponseDTO;
import com.liceman.application.user.infrastructure.dto.UserResponseWithoutTrainingDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    AuthenticationResponse createUser (UserRequestDTO userRequest);

    List<UserResponseWithoutTrainingDTO> findAllUsers (Pageable pageable);

    Optional<UserResponseDTO> getUserById (Long id);

    UserResponseDTO updateOwnUser (UserRequestDTO userRequestDTO);

    void deleteUserbyId (Long id);

    Optional<UserResponseWithoutTrainingDTO> getLoggedUser();
}
