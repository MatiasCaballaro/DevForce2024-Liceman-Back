package com.liceman.application.service;

import com.liceman.application.MockBuilder;
import com.liceman.application.shared.exceptions.EmailAlreadyExistsException;
import com.liceman.application.user.application.UserServiceImpl;
import com.liceman.application.user.domain.enums.Area;
import com.liceman.application.user.domain.enums.Role;
import com.liceman.application.user.domain.repository.UserRepository;
import com.liceman.application.user.infrastructure.dto.UserRequestDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.BDDMockito;

import static com.liceman.application.user.domain.enums.Role.MENTOR;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest2 {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Test
    void testMentorNoArea() throws Exception{

        // Creamos un objeto UserRequestDTO con Role.MENTOR y sin área
        UserRequestDTO request = MockBuilder.buildUserRequestDTO(MENTOR, null);

        //Ignoro la logica del metodo complejo
        BDDMockito.given(userRepository.existsByEmail(request.getEmail())).willReturn(false);

        // Verificamos que lanzará una IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(request));
    }
}
