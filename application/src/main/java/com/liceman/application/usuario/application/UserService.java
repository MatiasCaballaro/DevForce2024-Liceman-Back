package com.liceman.application.usuario.application;

import com.liceman.application.security.infrastructure.dto.AuthenticationResponse;
import com.liceman.application.usuario.infrastructure.dto.UserRequestDTO;
import com.liceman.application.usuario.infrastructure.dto.UserResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    AuthenticationResponse createUser (UserRequestDTO userRequest);

    List<UserResponseDTO> findAllUsers ();

    Optional<UserResponseDTO> getUserById (Long id);

    UserResponseDTO updateOwnUser (UserRequestDTO userRequestDTO);

    void deleteUserbyId (Long id);


}
