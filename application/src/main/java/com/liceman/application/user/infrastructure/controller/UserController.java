package com.liceman.application.user.infrastructure.controller;

import com.liceman.application.shared.exceptions.EmailAlreadyExistsException;
import com.liceman.application.shared.infrastructure.ResponseDTO;
import com.liceman.application.user.application.UserService;
import com.liceman.application.user.infrastructure.dto.UserRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            description = "Returns a list of users as a UserResponseDTO list. "
            //summary = ""
    )
    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<ResponseDTO> getAllUsers(@RequestParam(defaultValue = "0") Integer pageNumber,
                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                   @RequestParam(defaultValue = "id") String sortBy,
                                                   @RequestParam(defaultValue = "DESC") String orderBy) {
        try{
            return ResponseEntity.ok().body(
                    new ResponseDTO(true, "Users", userService.findAllUsers(pageNumber, pageSize, sortBy, orderBy)));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(
                    new ResponseDTO(false, e.getMessage(), null));
        }
    }

    @Operation(description = "Returns a single user as UserResponseDTO")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<ResponseDTO> getUserByID(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                new ResponseDTO(true, "User", userService.getUserById(id)));
    }

    @GetMapping("/my-user")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<ResponseDTO> getLoggedUser() {
        return ResponseEntity.ok().body(
                new ResponseDTO(true, "Logged user", userService.getLoggedUser()));
    }

    @Operation(description = "Create an user from an UserRequestDTO")
    @PostMapping
    @PreAuthorize("hasAuthority('user:create')")
    public ResponseEntity<ResponseDTO> createUser(@RequestBody UserRequestDTO request) {
        try {
            return ResponseEntity.ok().body(
                    new ResponseDTO(true, "User created!", userService.createUser(request)));
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(
                    new ResponseDTO(false, e.getMessage(), null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ResponseDTO(false, e.getClass().getSimpleName(), null));
        }

    }

    @Operation(description = "Update an user from an UserRequestDTO")
    @PutMapping
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok().body(
                new ResponseDTO(true, "User updated!", userService.updateOwnUser(userRequestDTO)));
    }

    @Operation(description = "Delete an user from an id (Long)")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        userService.deleteUserbyId(id);
        return ResponseEntity.ok()
                .body(new ResponseDTO(true, "User deleted", null));
    }
}
