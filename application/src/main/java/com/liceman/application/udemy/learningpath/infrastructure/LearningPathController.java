package com.liceman.application.udemy.learningpath.infrastructure;

import com.liceman.application.shared.infrastructure.ResponseDTO;
import com.liceman.application.udemy.learningpath.application.LearningPathService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/udemy/learning-paths")
@Tag(name = "Udemy - Learning Paths")
@RequiredArgsConstructor
public class LearningPathController {

    public final LearningPathService learningPathService;
    @Operation(description = "Return the learning paths data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    @GetMapping
    @PreAuthorize("hasAuthority('training:read')") // todo check authority
    public ResponseEntity<ResponseDTO> getCourses() {
        try {
            return ResponseEntity.ok().body(new ResponseDTO(
                    true,
                    "Learning Paths obtenidos" ,
                    learningPathService.getLearningPaths()));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ResponseDTO(
                    false,
                    "Error al intentar conectarse, intente nuevamente en unos instantes",
                    null));
        }
    }
    @Operation(description = "Return the learning path from a user by a Long id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('training:read')") // todo check authority
    public ResponseEntity<ResponseDTO> getCourseById(@PathVariable int id) {
        try {
            return ResponseEntity.ok().body(new ResponseDTO(
                    true,
                    "Información del Path: " + id,
                    learningPathService.getLearningPathById(id)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ResponseDTO(
                    false,
                    "Error al intentar conectarse, intente nuevamente en unos instantes",
                    null));
        }
    }
}
