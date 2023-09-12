package com.liceman.application.training.infrastructure;

import com.liceman.application.shared.application.mappers.MapperUtils;
import com.liceman.application.shared.infrastructure.ResponseDTO;
import com.liceman.application.training.application.TrainingEventService;
import com.liceman.application.training.application.TrainingService;
import com.liceman.application.training.infrastructure.dto.TrainingEventDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/trainings")
//@Tag(name = "TrainingEvent")
@RequiredArgsConstructor
public class TrainingEventController {

    private final TrainingEventService trainingEventService; //llamamos al servicio
    private final MapperUtils mapperUtils;

    @GetMapping("/{id}/training-events")
    @PreAuthorize("hasAnyAuthority('training:read')")
    public ResponseEntity<ResponseDTO> getTrainingEvents(@PathVariable Long id) { //id de training
        try {
            return ResponseEntity.ok().body(
                    new ResponseDTO(true,
                            "Training Events",
                            trainingEventService.getTrainingEvents(id).stream()
                                    .map(mapperUtils::mapperToTrainingEventDTO).collect(Collectors.toList())));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ResponseDTO(false, e.getClass().getSimpleName(), null));
        }
    }


}