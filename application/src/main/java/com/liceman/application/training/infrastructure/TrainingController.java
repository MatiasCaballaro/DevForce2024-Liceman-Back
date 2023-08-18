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


    @PostMapping
    @PreAuthorize("hasAnyAuthority('training:create')")
    public ResponseEntity<ResponseDTO> createTraining (@RequestBody TrainingCreationRequestDTO request) {
        return ResponseEntity.ok().body(
                new ResponseDTO(true,
                        "Training creada!",
                        mapperUtils.mapperToTrainingUserResponseDTO(trainingService.createTraining(request))));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('training:read')")
    public ResponseEntity<ResponseDTO> getTrainings (@RequestParam(defaultValue = "0") Integer pageNumber,
                                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                                     @RequestParam(defaultValue = "id") String sortBy,
                                                     @RequestParam(defaultValue = "DESC") String orderBy) {
        try {
            return ResponseEntity.ok().body(new ResponseDTO(true, "Trainings Obtenidas", trainingService.getTrainings(pageNumber, pageSize, sortBy, orderBy)
                    .stream()
                    .map(mapperUtils::mapperToTrainingUserResponseDTO).collect(Collectors.toList())));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('training:read')")
    public ResponseEntity<ResponseDTO> getTrainingByID (@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(
                    new ResponseDTO(true, "Trainings Obtenidas", mapperUtils.mapperToTrainingUserResponseDTO(trainingService.getTrainingById(id))));
        } catch (TrainingNotExistsException | IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new ResponseDTO(false, null, null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ResponseDTO(false, e.getClass().getSimpleName(), null));
        }

    }

    @Operation(
            description = "Enums de status que debería volver la operación del mentor : PENDIENTE_USER || RECHAZADA"
    )
    @PutMapping("/{id}/mentor")
    @PreAuthorize("hasAuthority('training:update')")
    public ResponseEntity<ResponseDTO> updateTrainingbyMentor (@PathVariable Long id, @RequestBody UpdateTrainingByMentorDTO updateTrainingByMentorDTO) {
        try {
            return ResponseEntity.ok()
                    .body(new ResponseDTO(true,
                            "Training Actualizada!",
                            mapperUtils.mapperToTrainingUserResponseDTO(trainingService.mentorUpdateTraining(id, updateTrainingByMentorDTO))));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ResponseDTO(false, e.getMessage(), null));
        }

    }

    @Operation(
            description = "Enums de status que debería volver la operación del mentor : PENDIENTE_ADMIN || RECHAZADA"
    )
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
            description = "Enums de status que debería volver la operación del mentor : APROBADA || RECHAZADA"
    )
    @PutMapping("/{id}/admin")
    @PreAuthorize("hasAuthority('training:update')")
    public ResponseEntity<ResponseDTO> updateTrainingbyAdmin (@PathVariable Long id,@RequestBody UpdateTrainingByAdminDTO updateTrainingByAdminDTO) {
        try {
            return ResponseEntity.ok()
                    .body(new ResponseDTO(true,
                            "Training Actualizada!",
                            mapperUtils.mapperToTrainingUserResponseDTO(trainingService.adminUpdateTraining(id, updateTrainingByAdminDTO))));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ResponseDTO(false, e.getClass().getSimpleName(), null));
        }
    }


}



