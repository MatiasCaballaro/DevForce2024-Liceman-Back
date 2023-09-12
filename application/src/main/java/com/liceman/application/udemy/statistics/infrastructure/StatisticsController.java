package com.liceman.application.udemy.statistics.infrastructure;

import com.liceman.application.shared.infrastructure.ResponseDTO;
import com.liceman.application.udemy.statistics.application.StatisticsService;
import com.liceman.application.udemy.statistics.domain.ActivityResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/udemy/statistics")
@Tag(name = "Udemy - Statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);
    private final StatisticsService statisticsService;
    @Operation(description = "Return the general statistics of a user from an email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),

    })
    @GetMapping("/activity")
    @PreAuthorize("hasAuthority('training:read')") // todo check authority
    public ResponseEntity<ResponseDTO> getUserStatistics(@RequestParam String email) {
        try {
            logger.info("Fetching user activity statistics for email: {}", email);
            ActivityResult activityResult = statisticsService.getUserActivity(email);
            if (activityResult == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
            return ResponseEntity.ok().body(new ResponseDTO(true, "User general statistics", activityResult));
        } catch (Exception e) {
            logger.error("Error while fetching user statistics: {} {}", e.getClass(), e.getMessage());
            return ResponseEntity.ok().body(new ResponseDTO(
                    false,
                    "Error trying to connect, please try again in a few moments",
                    null));
        }

    }

    @Operation(description = "Return the statistics of the user's courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    @GetMapping("/activity-courses")
    @PreAuthorize("hasAuthority('training:read')") // todo check authority
    public ResponseEntity<ResponseDTO> getUserCourseStatistics(@RequestParam String email) {
        try {
            return ResponseEntity.ok().body(new ResponseDTO(
                    true,
                    "User's course statistics",
                    statisticsService.getCoursesActivity(email)));
        } catch (Exception e) {
            logger.error("Error while fetching user course statistics: {} {}", e.getClass(), e.getMessage());
            return ResponseEntity.ok().body(new ResponseDTO(
                    false,
                    "Error trying to connect, please try again in a few moments",
                    null));
        }

    }






}
