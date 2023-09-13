package com.liceman.application.service;
import com.liceman.application.user.domain.enums.Area;
import com.liceman.application.security.application.JwtService;
import com.liceman.application.security.infrastructure.dto.AuthenticationResponse;
import com.liceman.application.user.application.UserService;
import com.liceman.application.user.application.UserServiceImpl;
import com.liceman.application.user.domain.User;
import com.liceman.application.user.domain.enums.Area;
import com.liceman.application.user.domain.enums.Role;
import com.liceman.application.user.domain.repository.UserRepository;
import com.liceman.application.security.domain.token.TokenRepository;
import com.liceman.application.user.infrastructure.dto.UserRequestDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class) // We inform that it uses the Mockito extension.
public class UserServiceImplTest3 {

    @InjectMocks // An instance of the service is invoked. Only once.
    UserServiceImpl userServiceImpl;

    @Mock // Creates a MOCK of the userRepository
        // All class dependencies should be declared here
    UserRepository userRepository;

/*
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtService jwtService;
    @Mock
    TokenRepository tokenRepository;

    @Test
    void createUser_OK() {
        // Setup our mock repository
         // Parameters for the function under test
        UserRequestDTO request = new UserRequestDTO();
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("password123");
        request.setRole(Role.USER);
        request.setHasTeams(true);

        // Call the MockBuilder
         // The object against which you'll later compare your response
        AuthenticationResponse responseTest = AuthenticationResponse.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();

        // Ignore the logic of the complex method
         // Simulate the behavior of the methods used within the method under test
        User savedUser = new User();
        BDDMockito.given(userRepository.save(BDDMockito.any(User.class))).willReturn(savedUser);
        BDDMockito.given(userRepository.existsByEmail(request.getEmail())).willReturn(false); // User does not exist
        BDDMockito.given(jwtService.generateToken(BDDMockito.any(User.class))).willReturn("accessToken"); // Mock token generation
        BDDMockito.given(jwtService.generateRefreshToken(BDDMockito.any(User.class))).willReturn("refreshToken"); // Mock refresh token generation
        //BDDMockito.given(passwordEncoder.encode(request.getPassword())).willReturn(Mockito.anyString()); // Mock refresh token generation

        // Execute the service call
        AuthenticationResponse response = userServiceImpl.createUser(request);

        // Assert the response
        Assertions.assertEquals(responseTest.getAccessToken(), response.getAccessToken());
        Assertions.assertEquals(responseTest.getRefreshToken(), response.getRefreshToken());

    }
 */
    @Test
    void createUser_userWithArea(){
        // Setup our mock repository
            // Parameters for the function under test
        UserRequestDTO request = new UserRequestDTO();
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("password123");
        request.setRole(Role.USER); // User with a role of USER
        request.setArea(Area.BACKEND); // Set an area for this user
        request.setHasTeams(true);

        // Ignore the logic of the complex method
            // Simulate the behavior of the methods used within the method under test
        BDDMockito.given(userRepository.existsByEmail(request.getEmail())).willReturn(false); // User does not exist

        // Error handling logic
        try {
            // Execute the service call
            AuthenticationResponse response = userServiceImpl.createUser(request);

        } catch (IllegalArgumentException e){

            // Assert the response
            Assertions.assertEquals(IllegalArgumentException.class, e.getClass());

        }

    }

}
