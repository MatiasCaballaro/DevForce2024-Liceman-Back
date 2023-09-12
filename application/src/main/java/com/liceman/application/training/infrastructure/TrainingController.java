package com.liceman.application.training.infrastructure;

import com.liceman.application.shared.application.mappers.MapperUtils;
import com.liceman.application.shared.exceptions.TrainingNotExistsException;
import com.liceman.application.shared.infrastructure.ResponseDTO;
import com.liceman.application.training.application.TrainingService;
import com.liceman.application.training.infrastructure.dto.TrainingCreationRequestDTO;
import com.liceman.application.training.infrastructure.dto.UpdateTrainingByAdminDTO;
import com.liceman.application.training.infrastructure.dto.UpdateTrainingByMentorDTO;
import com.liceman.application.training.infrastructure.dto.UpdateTrainingByUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/trainings")
@Tag(name = "Trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;
    private final MapperUtils mapperUtils;

    @Operation(description = "Create a training from TrainingCreationRequestDTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    @PostMapping
    @PreAuthorize("hasAnyAuthority('training:create')")
    public ResponseEntity<ResponseDTO> createTraining (@RequestBody TrainingCreationRequestDTO request) {
        return ResponseEntity.ok().body(
                new ResponseDTO(true,
                        "Training created!",
                        mapperUtils.mapperToTrainingUserResponseDTO(trainingService.createTraining(request))));
    }
    @Operation(description = "Return a list of trainings sorted in descending order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    @GetMapping
    @PreAuthorize("hasAuthority('training:read')")
    public ResponseEntity<ResponseDTO> getTrainings (@RequestParam(defaultValue = "0") Integer pageNumber,
                                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                                     @RequestParam(defaultValue = "id") String sortBy,
                                                     @RequestParam(defaultValue = "DESC") String orderBy) {
        try {
            return ResponseEntity.ok().body(new ResponseDTO(true, "Trainings", trainingService.getTrainings(pageNumber, pageSize, sortBy, orderBy)
                    .stream()
                    .map(mapperUtils::mapperToTrainingUserResponseDTO).collect(Collectors.toList())));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(false, e.getMessage(), null));
        }
    }
    @Operation(description = "Return training by Long id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('training:read')")
    public ResponseEntity<ResponseDTO> getTrainingByID (@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(
                    new ResponseDTO(true, "Trainings", mapperUtils.mapperToTrainingUserResponseDTO(trainingService.getTrainingById(id))));
        } catch (TrainingNotExistsException | IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new ResponseDTO(false, null, null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ResponseDTO(false, e.getClass().getSimpleName(), null));
        }

    }

    @Operation(
            description = "Enums of statuses that the mentor operation should return: PENDING_USER || REJECTED"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/{id}/mentor")
    @PreAuthorize("hasAuthority('training:update')")
    public ResponseEntity<ResponseDTO> updateTrainingbyMentor (@PathVariable Long id, @RequestBody UpdateTrainingByMentorDTO updateTrainingByMentorDTO) {
        try {
            return ResponseEntity.ok()
                    .body(new ResponseDTO(true,
                            "Training updated!",
                            mapperUtils.mapperToTrainingUserResponseDTO(trainingService.mentorUpdateTraining(id, updateTrainingByMentorDTO))));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ResponseDTO(false, e.getMessage(), null));
        }

    }

    @Operation(
            description = "Enums of statuses that the mentor operation should return: PENDING_ADMIN || REJECTED"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/{id}/user")
    @PreAuthorize("hasAuthority('training:update')")
    public ResponseEntity<ResponseDTO> updateTrainingbyUser (@PathVariable Long id, @RequestBody UpdateTrainingByUserDTO updateTrainingByUserDTO) {
        try {
            return ResponseEntity.ok()
                    .body(new ResponseDTO(true,
                            "Training Actualizada!",
                            mapperUtils.mapperToTrainingUserResponseDTO(trainingService.userUpdateTraining(id, updateTrainingByUserDTO))));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ResponseDTO(false, e.getClass().getSimpleName(), null));
        }
    }


    @Operation(
            description = "Enums of statuses that the mentor operation should return: APPROVED || REJECTED"
    )
    @PutMapping("/{id}/admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasAuthority('training:update')")
    public ResponseEntity<ResponseDTO> updateTrainingbyAdmin (@PathVariable Long id,@RequestBody UpdateTrainingByAdminDTO updateTrainingByAdminDTO) {
        try {
            return ResponseEntity.ok()
                    .body(new ResponseDTO(true,
                            "Training updated!",
                            mapperUtils.mapperToTrainingUserResponseDTO(trainingService.adminUpdateTraining(id, updateTrainingByAdminDTO))));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ResponseDTO(false, e.getClass().getSimpleName(), null));
        }
    }


}



