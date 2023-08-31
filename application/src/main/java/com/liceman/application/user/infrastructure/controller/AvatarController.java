package com.liceman.application.user.infrastructure.controller;

import com.liceman.application.shared.exceptions.NotValidImageFormatException;
import com.liceman.application.shared.infrastructure.ResponseDTO;
import com.liceman.application.udemy.course.application.CourseServiceImpl;
import com.liceman.application.user.application.AvatarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users/avatars")
@Tag(name = "Avatars")
@RequiredArgsConstructor
public class AvatarController {

    private static final Logger logger = LoggerFactory.getLogger(AvatarController.class);

    private final AvatarService avatarService;

    @Operation(
            description = "Sube una foto del usuario en formato image "
            // summary = ""
    )
    @PostMapping("/Avatar")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<ResponseDTO> uploadAvatar(@RequestBody Map<String,String> image) {
        try {
            logger.info("Uploading avatar for user");
            avatarService.uploadAvatar(image.get("image"));
            return ResponseEntity.ok().body(new ResponseDTO(true, "Avatar creado", null));
        } catch (NotValidImageFormatException e) {
            logger.error("Error uploading avatar: {} {}", e.getClass(), e.getMessage());
            return ResponseEntity.badRequest().body(
                    new ResponseDTO(false, e.getMessage(), null));
        } catch (IOException e) {
            logger.error("Error uploading avatar: {} {}", e.getClass(), e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseDTO(false, "Error al intentar subir el avatar", null));
        }
    }

    @Operation(
            description = "Devuelte el Avatar encodeado en base64 para el usuario logueado" + "\n\n" +
                    "Para revisar la imagen que genera, utilizar https://codebeautify.org/base64-to-image-converter"
    )
    @GetMapping("/Avatar/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<ResponseDTO> getAvatar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(new ResponseDTO(true, "Avatar obtained succesfully", avatarService.getAvatar(id)));
        } catch (FileNotFoundException e) {
            logger.error("Avatar not found for user: {} {}", e.getClass(), e.getMessage());
            return ResponseEntity.ok().body(new ResponseDTO(false, "Avatar not found", null));
        }
    }
}
