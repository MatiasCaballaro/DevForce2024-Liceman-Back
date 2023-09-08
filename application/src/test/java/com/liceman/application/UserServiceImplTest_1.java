package com.liceman.application;
import com.liceman.application.shared.exceptions.EmailAlreadyExistsException;
import com.liceman.application.user.application.UserServiceImpl;
import com.liceman.application.user.domain.repository.UserRepository;
import com.liceman.application.user.infrastructure.dto.UserRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest_1 {
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Test
    void testCreateUser_existsEmail() {
        UserRequestDTO request = new UserRequestDTO();
        request.setEmail("existing@example.com");
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);
        assertThrows(EmailAlreadyExistsException.class, () -> {
            userService.createUser(request);
        });
    }
}
