package com.liceman.application.udemy.course.infrastructure;

import com.liceman.application.shared.infrastructure.ResponseDTO;
import com.liceman.application.udemy.course.application.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/udemy/courses")
@Tag(name = "Udemy - Courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    @PreAuthorize("hasAuthority('training:read')") // todo check authority
    public ResponseEntity<ResponseDTO> getCourses() {
        try {
            return ResponseEntity.ok().body(new ResponseDTO(
                    true,
                    "Listado de Cursos:",
                    courseService.getCourses()));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ResponseDTO(
                    false,
                    "Error al intentar conectarse, intente nuevamente en unos instantes",
                    null));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('training:read')") // todo check authority
    public ResponseEntity<ResponseDTO> getCourseById(@PathVariable int id) {
        try {
            return ResponseEntity.ok().body(new ResponseDTO(
                    true,
                    "Información del curso: " + id,
                    courseService.getCourseById(id)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ResponseDTO(
                    false,
                    "Error al intentar conectarse, intente nuevamente en unos instantes",
                    null));
        }
    }
}
