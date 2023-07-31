package com.liceman.application.user.infrastructure.controller;

import com.liceman.application.shared.exceptions.NotValidImageFormatException;
import com.liceman.application.shared.infrastructure.ResponseDTO;
import com.liceman.application.user.application.AvatarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/users/avatars")
@Tag(name = "Avatars")
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarService avatarService;

    @Operation(
            description = "Sube una foto del usuario en formato image "
            // summary = ""
    )
    @PostMapping("/Avatar")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<ResponseDTO> uploadAvatar(@RequestParam("image") MultipartFile file) {
        try {
            avatarService.uploadAvatar(file);
            return ResponseEntity.ok().body(new ResponseDTO(true, "Avatar creado", null));
        } catch (NotValidImageFormatException e) {
            return ResponseEntity.badRequest().body(
                    new ResponseDTO(false, e.getMessage(), null));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(
                    new ResponseDTO(false, "Error al intentar subir el avatar", null));
        }
    }

    @Operation(
            description = "Devuelte el Avatar encodeado en base64 para el usuario logueado" + "\n\n" +
                    "Para revisar la imagen que genera, utilizar https://codebeautify.org/base64-to-image-converter"
    )
    @GetMapping("/Avatar")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<ResponseDTO> getAvatar() {
        try {
            return ResponseEntity.ok().body(new ResponseDTO(true, "Avatar obtained succesfully", avatarService.getAvatar()));
        } catch (FileNotFoundException e) {
            return ResponseEntity.ok().body(new ResponseDTO(false, "Avatar not found", null));
        }
    }
}
