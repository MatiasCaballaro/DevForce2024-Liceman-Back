package com.liceman.application.security.infrastructure.controller;

import com.liceman.application.security.application.AuthenticationService;
import com.liceman.application.security.infrastructure.dto.AuthenticationRequest;
import com.liceman.application.security.infrastructure.dto.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @Operation(
            description = "Authenticate the user. If the user doesn't have a site-registered email or password, they will not be granted access"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),

    })
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @Operation(
            description = "Refresh token to guarantee to user new secure pass."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
    })
    @PostMapping("/refresh-token")
    public void refreshToken (HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }


}
