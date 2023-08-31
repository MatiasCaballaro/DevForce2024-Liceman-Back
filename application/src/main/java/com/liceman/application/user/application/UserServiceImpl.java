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
import com.liceman.application.udemy.course.application.CourseServiceImpl;
import com.liceman.application.user.domain.User;
import com.liceman.application.user.domain.enums.Role;
import com.liceman.application.user.domain.repository.UserRepository;
import com.liceman.application.user.infrastructure.dto.UserRequestDTO;
import com.liceman.application.user.infrastructure.dto.UserResponseDTO;
import com.liceman.application.user.infrastructure.dto.UserResponseWithoutTrainingDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final AvatarService avatarService;

    private final MapperUtils mapperUtils;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
        logger.info("User created: \n{}\nAccess Token: {}\nRefresh Token: {}", savedUser, jwtToken, refreshToken);
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
        logger.info("User token saved");
        tokenRepository.save(token);

    }



    @Override
    public List<UserResponseWithoutTrainingDTO> findAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String orderBy) throws IllegalArgumentException {
        Sort sort = Sort.by(sortBy);
        if ("DESC".equalsIgnoreCase(orderBy))
            sort = sort.descending();
        else if ("ASC".equalsIgnoreCase(orderBy))
            sort = sort.ascending();
        else
            throw new IllegalArgumentException("Invalid orderBy value. It should be either ASC or DESC.");

        List<UserResponseWithoutTrainingDTO> users = userRepository.findAll(PageRequest.of(pageNumber, pageSize, sort))
                .stream()
                .map(mapperUtils::mapperToUserWithoutTrainingDTO)
                .collect(Collectors.toList());
        StringBuilder allUsers = new StringBuilder();
        for (UserResponseWithoutTrainingDTO userResponse : users) {
            allUsers.append(userResponse).append("\n");
        }
        logger.info("Getting all Users: \n{}", allUsers);

        if(users.isEmpty()) {
            logger.info("No more content for this page");
            throw new IllegalArgumentException("no hay mas contenido para esta pagina");
        }
        return users;
    }

    @LoggedUser
    @Override
    public Optional<UserResponseWithoutTrainingDTO> getLoggedUser(){
        User user = UserContext.getUser();
        Optional<UserResponseWithoutTrainingDTO> userDTO = Optional.ofNullable(mapperUtils.mapperToUserWithoutTrainingDTO(UserContext.getUser()));
        logger.info("Getting logged user: {}", userDTO.get());
        return userDTO;
    }

    @Override
    public Optional<UserResponseDTO> getUserById (Long id) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(id).orElseThrow(RuntimeException::new));
        Optional<UserResponseDTO> userDTO = user.map(mapperUtils::mapperToUserDTO);
        logger.info("Getting user: {}", userDTO.get());
        return userDTO;
    }

    //El método UpdateOwnUser no involucra el cambio de mail
    @LoggedUser
    @Override
    public UserResponseDTO updateOwnUser (UserRequestDTO userRequestDTO) {
        User loggedUser = UserContext.getUser();
        UpdateUserData(userRequestDTO, loggedUser);
        Optional<UserResponseDTO> userUpdatedDTO = Optional.ofNullable(mapperUtils.mapperToUserDTO(userRepository.save(loggedUser)));
        logger.info("Updating own user: {}", userUpdatedDTO.get());
        return userUpdatedDTO.get();
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
        logger.info("User deleted: {}", userRepository.findById(id));
        userRepository.deleteById(id);
    }


}
