package com.liceman.application;
import com.liceman.application.shared.application.loggeduser.UserContext;
import com.liceman.application.training.application.TrainingServiceImpl;
import com.liceman.application.training.domain.Comment;
import com.liceman.application.training.domain.Training;
import com.liceman.application.training.domain.enums.Status;
import com.liceman.application.training.domain.repository.TrainingRepository;
import com.liceman.application.training.infrastructure.dto.TrainingCreationRequestDTO;
import com.liceman.application.user.domain.User;
import com.liceman.application.user.domain.enums.Area;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TrainingServiceImpTest_1 {

    @InjectMocks
    TrainingServiceImpl trainingService;

    @Mock
    TrainingRepository trainingRepository;

    @Test
    void testCreateTrainingOk() {

        TrainingCreationRequestDTO requestDTO = new TrainingCreationRequestDTO();
        requestDTO.setArea(Area.BACKEND);
        requestDTO.setTitle("Título de prueba");
        requestDTO.setComment("Comentario de prueba");
        UserContext.setUser(new User());

        when(trainingRepository.save(any(Training.class))).thenAnswer(invocation -> {
            Training savedTraining = invocation.getArgument(0);
            savedTraining.setId(1L);
            return savedTraining;
        });

        Training createdTraining = trainingService.createTraining(requestDTO);

        assertNotNull(createdTraining);
        assertEquals(Area.BACKEND, createdTraining.getArea());
        assertEquals("Título de prueba", createdTraining.getTitle());
        assertEquals(UserContext.getUser(), createdTraining.getUserId());
        assertEquals(LocalDateTime.now().getDayOfYear(), createdTraining.getCreationDate().getDayOfYear());

        assertFalse(createdTraining.getComments().isEmpty());
        Comment comment = createdTraining.getComments().get(0);
        assertEquals(requestDTO.getComment(), comment.getMessage());

    }
}
