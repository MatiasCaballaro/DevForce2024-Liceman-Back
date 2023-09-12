package com.liceman.application.udemy.course.infrastructure;

import com.liceman.application.shared.infrastructure.ResponseDTO;
import com.liceman.application.udemy.course.application.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Operation(description = "Return a list of courses")
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
                    "Course list",
                    courseService.getCourses()));
        } catch (Exception e) {
            logger.error("Error while fetching courses: {} {}", e.getClass(), e.getMessage());
            return ResponseEntity.ok().body(new ResponseDTO(
                    false,
                    "Error trying to connect, please try again in a few moments",
                    null));
        }
    }

    @Operation(description = "Return courses by a long id")
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
                    "Course info: " + id,
                    courseService.getCourseById(id)));
        } catch (Exception e) {
            logger.error("Error while fetching course with ID {}: {} {}", id, e.getClass(), e.getMessage());
            return ResponseEntity.ok().body(new ResponseDTO(
                    false,
                    "Error trying to connect, please try again in a few moments",
                    null));
        }
    }
}
