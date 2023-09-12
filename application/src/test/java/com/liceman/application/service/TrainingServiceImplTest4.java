package com.liceman.application.service;

import com.liceman.application.training.application.TrainingServiceImpl;
import com.liceman.application.training.domain.repository.TrainingRepository;
import com.liceman.application.training.infrastructure.dto.UpdateTrainingByMentorDTO;
import com.liceman.application.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
public class TrainingServiceImplTest4 {

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void mentorUpdateTraining_NoTraining() throws Exception {

        //Setup our mock repository
        Long id = 1L;
        UpdateTrainingByMentorDTO request = new UpdateTrainingByMentorDTO();

        // Excute the service call
        assertThrows(IllegalStateException.class, () -> {
            trainingService.mentorUpdateTraining(id, request);
        });
    }

    @Test
    public void mentorUpdateTraining_Exception() throws Exception {
        //Setup our mock repository
        Long id = 2L;
        UpdateTrainingByMentorDTO request = new UpdateTrainingByMentorDTO();

        // Excute the service call
        assertThrows(Exception.class, () -> {
            trainingService.mentorUpdateTraining(id, request);
        });
    }
}