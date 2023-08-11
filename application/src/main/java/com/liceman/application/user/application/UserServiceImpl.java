package com.liceman.application.user.application;

import com.liceman.application.security.application.JwtService;
import com.liceman.application.security.domain.token.Token;
import com.liceman.application.security.domain.token.TokenRepository;
import com.liceman.application.security.domain.token.TokenType;
import com.liceman.application.security.infrastructure.dto.AuthenticationResponse;
import com.liceman.application.shared.application.loggeduser.LoggedUser;
import com.liceman.application.shared.application.loggeduser.UserContext;
import com.liceman.application.shared.application.mappers.MapperUtils;
import com.liceman.application.shared.exceptions.EmailAlreadyExistsException;
import com.liceman.application.user.domain.User;
import com.liceman.application.user.domain.enums.Role;
import com.liceman.application.user.domain.repository.UserRepository;
import com.liceman.application.user.infrastructure.dto.UserRequestDTO;
import com.liceman.application.user.infrastructure.dto.UserResponseDTO;
import com.liceman.application.user.infrastructure.dto.UserResponseWithoutTrainingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final MapperUtils mapperUtils;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;


    @Override
    public AuthenticationResponse createUser (UserRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException();
        }
        if(request.getRole()==Role.MENTOR && request.getArea()==null || request.getRole()!=Role.MENTOR && request.getArea()!=null){
            throw new IllegalArgumentException();
        }
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .hasTeams(request.getHasTeams())
                .build();
        if(request.getArea()!=null){
            user.setArea(request.getArea());
        }
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken (User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }


    @Override
    public List<UserResponseWithoutTrainingDTO> findAllUsers (Pageable pageable) {
        List<UserResponseWithoutTrainingDTO> users = userRepository.findAll(pageable)
                .stream()
                .map(mapperUtils::MapperToUserWithoutTrainingDTO)
                .collect(Collectors.toList());
        if(users.isEmpty())
            throw new IllegalArgumentException("No hay mas contenido para esta pagina");
        return users;
    }

    @Override
    public Optional<UserResponseDTO> getUserById (Long id) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(id).orElseThrow(RuntimeException::new));
        return user.map(mapperUtils::MapperToUserDTO);
    }
    @LoggedUser
    @Override
    public Optional<UserResponseWithoutTrainingDTO> getLoggedUser(){
        return Optional.ofNullable(mapperUtils.MapperToUserWithoutTrainingDTO(UserContext.getUser()));
    }

    //El método UpdateOwnUser no involucra el cambio de mail
    @LoggedUser
    @Override
    public UserResponseDTO updateOwnUser (UserRequestDTO userRequestDTO) {
        User loggedUser = UserContext.getUser();
        UpdateUserData(userRequestDTO, loggedUser);
        return mapperUtils.MapperToUserDTO(userRepository.save(loggedUser));
    }

    private void UpdateUserData (UserRequestDTO userRequestDTO, User user) {
        user.setFirstname(userRequestDTO.getFirstname());
        user.setLastname(userRequestDTO.getLastname());
        if (userRequestDTO.getPassword() != null && !(userRequestDTO.getPassword().isEmpty())) {
            user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        }
        user.setPhone(userRequestDTO.getPhone());
        user.setHasTeams(userRequestDTO.getHasTeams());
    }

    @Override
    public void deleteUserbyId (Long id) {
        userRepository.deleteById(id);
    }


}
