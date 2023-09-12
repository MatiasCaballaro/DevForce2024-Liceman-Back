package com.liceman.application.user.infrastructure.controller;

import com.liceman.application.shared.exceptions.EmailAlreadyExistsException;
import com.liceman.application.shared.infrastructure.ResponseDTO;
import com.liceman.application.udemy.course.application.CourseServiceImpl;
import com.liceman.application.user.application.UserService;
import com.liceman.application.user.infrastructure.dto.UserRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Operation(
            description = "Returns the total list of users as UserResponseDTO. " +
                    "if an email is included as a parameter, search for a single user"//,
            //summary = ""
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<ResponseDTO> getAllUsers(@RequestParam(defaultValue = "0") Integer pageNumber,
                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                   @RequestParam(defaultValue = "id") String sortBy,
                                                   @RequestParam(defaultValue = "DESC") String orderBy) {
        try{
            logger.info("Getting all users");
            return ResponseEntity.ok().body(
                    new ResponseDTO(true, "Users", userService.findAllUsers(pageNumber, pageSize, sortBy, orderBy)));
        }catch (IllegalArgumentException e){
            logger.error("Error getting all users: {}", e.getMessage());
            return ResponseEntity.badRequest().body(
                    new ResponseDTO(false, e.getMessage(), null));
        }
    }

    @Operation(description = "Return a user from Long id as a UserResponseDTO")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<ResponseDTO> getUserByID(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                new ResponseDTO(true, "User", userService.getUserById(id)));
    }

    @Operation(description = "Return a logged user as a UserResponseDTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/my-user")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<ResponseDTO> getLoggedUser() {
        return ResponseEntity.ok().body(
                new ResponseDTO(true, "Logged user", userService.getLoggedUser()));
    }

    @Operation(description = "Create a user from UserRequestDTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    @PreAuthorize("hasAuthority('user:create')")
    public ResponseEntity<ResponseDTO> createUser(@RequestBody UserRequestDTO request) {
        try {
            logger.info("Creating user");
            return ResponseEntity.ok().body(
                    new ResponseDTO(true, "User created!", userService.createUser(request)));
        } catch (EmailAlreadyExistsException e) {
            logger.error("Error creating user: {} {} ", e.getClass(), e.getMessage());
            return ResponseEntity.badRequest().body(
                    new ResponseDTO(false, e.getMessage(), null));
        } catch (IllegalArgumentException e) {
            logger.error("Error creating user: {} {} ", e.getClass(), e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            logger.error("Error creating user: {} {} ", e.getClass(), e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseDTO(false, e.getClass().getSimpleName(), null));
        }

    }

    @Operation(description = "Update a user from UserRequestDTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    @PutMapping
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok().body(
                new ResponseDTO(true, "User updated!", userService.updateOwnUser(userRequestDTO)));
    }

    @Operation(description = "Delete a user from Long id")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    @PreAuthorize("hasAuthority('user:delete')")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        logger.info("Deleting user with ID: {}", id);
        userService.deleteUserbyId(id);
        return ResponseEntity.ok()
                .body(new ResponseDTO(true, "User deleted", null));
    }
}
