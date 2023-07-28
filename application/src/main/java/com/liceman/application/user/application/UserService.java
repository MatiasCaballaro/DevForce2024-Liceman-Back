package com.liceman.application.user.application;

import com.liceman.application.security.infrastructure.dto.AuthenticationResponse;
import com.liceman.application.shared.application.loggeduser.LoggedUser;
import com.liceman.application.shared.infrastructure.ResponseDTO;
import com.liceman.application.user.infrastructure.dto.UserRequestDTO;
import com.liceman.application.user.infrastructure.dto.UserResponseDTO;
import com.liceman.application.user.infrastructure.dto.UserResponseWithoutTrainingDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {

    @LoggedUser
    ResponseDTO uploadAvatar (MultipartFile file);

    @LoggedUser
    ResponseDTO getAvatar();

    AuthenticationResponse createUser (UserRequestDTO userRequest);

    List<UserResponseDTO> findAllUsers ();

    Optional<UserResponseDTO> getUserById (Long id);

    UserResponseDTO updateOwnUser (UserRequestDTO userRequestDTO);

    void deleteUserbyId (Long id);

    Optional<UserResponseWithoutTrainingDTO> getLoggedUser();
}
