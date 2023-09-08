package com.liceman.application.service;
import com.liceman.application.user.application.UserServiceImpl;
import com.liceman.application.user.domain.User;
import com.liceman.application.user.infrastructure.dto.UserRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest4  {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Test
    public void testUpdateUserData () {
        // Setup our mock repository
        UserRequestDTO userRequestDTO = MockBuilder.buildUserRequestDTODefault();
        User user = new User();
        when(passwordEncoder.encode("123")).thenReturn("hashedPassword");

        // Excute the service call
        userService.UpdateUserData(userRequestDTO, user);

        // Assert the response
        assertEquals("Manuel", user.getFirstname());
        assertEquals("Lopez", user.getLastname());
        assertEquals("hashedPassword", user.getPassword());
        assertEquals("49033456", user.getPhone());
        assertTrue(user.getHasTeams());
    }
}
