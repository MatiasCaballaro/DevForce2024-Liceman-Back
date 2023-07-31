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
import com.liceman.application.shared.infrastructure.ResponseDTO;
import com.liceman.application.user.domain.User;
import com.liceman.application.user.domain.enums.Role;
import com.liceman.application.user.domain.repository.UserRepository;
import com.liceman.application.user.infrastructure.dto.UserRequestDTO;
import com.liceman.application.user.infrastructure.dto.UserResponseDTO;
import com.liceman.application.user.infrastructure.dto.UserResponseWithoutTrainingDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Value("${avatar.baseRoute}")
    private String BaseRoute;
    private final TokenRepository tokenRepository;

    private final MapperUtils mapperUtils;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;




    @LoggedUser
    @Override
    public ResponseDTO uploadAvatar(MultipartFile file) {
        OutputStream outputStream = null;
        try{
            if (file.isEmpty()||!file.getContentType().contains("image/")){
                return new ResponseDTO(false,"Avatar not Uploaded","Formato de archivo incorrecto");

            }
            File file1 = new File(BaseRoute+UserContext.getUser().getId());
            outputStream = new BufferedOutputStream(new FileOutputStream(file1));
            outputStream.write(file.getBytes());

            return new ResponseDTO(true,"Avatar uploaded correctly",null);

        }
        catch (Exception e){
            return new ResponseDTO(false,"Avatar not Uploaded",e.getMessage());
        }finally {
            try {
                outputStream.close();
            }catch (Exception ignored){}
        }
    }
    @LoggedUser
    @Override
    public ResponseDTO getAvatar()  {
        InputStream inputStream = null;
        try{
            File file = new File(BaseRoute+UserContext.getUser().getId());
            inputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] dataByte = inputStream.readAllBytes();
            inputStream.close();
            return new ResponseDTO(true
                    ,"User avatar obtained succesfully"
                    , Base64.getEncoder().encodeToString(dataByte));

        }
        catch (Exception e) {
            System.out.println(e);
            return new ResponseDTO(true
                    ,"User avatar cannot be obtained"
                    , e.getMessage());
        }finally {
            try {
                inputStream.close();
            }catch (Exception ignored){}
        }
    }



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
    public List<UserResponseDTO> findAllUsers () {
        return userRepository.findAll()
                .stream()
                .map(mapperUtils::mapperToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserResponseDTO> getUserById (Long id) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(id).orElseThrow(RuntimeException::new));
        return user.map(mapperUtils::mapperToUserDTO);
    }
    @LoggedUser
    @Override
    public Optional<UserResponseWithoutTrainingDTO> getLoggedUser(){
        return Optional.ofNullable(mapperUtils.mapperToUserWithoutTrainingDTO(UserContext.getUser()));
    }

    //El método UpdateOwnUser no involucra el cambio de mail
    @LoggedUser
    @Override
    public UserResponseDTO updateOwnUser (UserRequestDTO userRequestDTO) {
        User loggedUser = UserContext.getUser();
        UpdateUserData(userRequestDTO, loggedUser);
        return mapperUtils.mapperToUserDTO(userRepository.save(loggedUser));
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
