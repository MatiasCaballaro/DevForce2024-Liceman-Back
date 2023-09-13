package com.liceman.application.service;

import com.liceman.application.MockBuilder;
import com.liceman.application.security.application.JwtService;
import com.liceman.application.security.domain.token.TokenRepository;
import com.liceman.application.security.infrastructure.dto.AuthenticationResponse;
import com.liceman.application.user.application.UserServiceImpl;
import com.liceman.application.user.domain.User;
import com.liceman.application.user.domain.enums.Role;
import com.liceman.application.user.domain.repository.UserRepository;
import com.liceman.application.user.infrastructure.dto.UserRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest5 {

    @InjectMocks //Se llama una instancia del servicio. Una unica vez.
    UserServiceImpl userServiceImpl;
    @Mock
    UserRepository userRepository;
    @Mock
    TokenRepository tokenRepository;
    @Mock
    JwtService jwtService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void CreateUser_OK() {

        //Llamo al MockBuilder
        UserRequestDTO userRequestDTO = MockBuilder.buildUserOkPHResponseDefault();

        //Ignoro la logica del metodo complejo
        BDDMockito.given(userRepository.existsByEmail(userRequestDTO.getEmail())).willReturn(false);
        BDDMockito.given(passwordEncoder.encode(userRequestDTO.getPassword())).willReturn("passwordEncoded");
        BDDMockito.given(jwtService.generateRefreshToken(BDDMockito.any())).willReturn("generatedRefreshToken");
        BDDMockito.given(jwtService.generateToken(BDDMockito.any())).willReturn("generatedToken");

        // Execute the service call
        AuthenticationResponse response = userServiceImpl.createUser(userRequestDTO);

        // Assert the response
        assertThat(response).isNotNull(); // Asegúrate de que la respuesta no sea nula
        assertThat(response.getAccessToken()).isNotBlank(); // Asegúrate de que el token de acceso no esté en blanco
        assertThat(response.getRefreshToken()).isNotBlank(); // Asegúrate de que el token de actualización no esté en blanco
    }
}