package com.liceman.application.service;

import com.liceman.application.training.application.TrainingServiceImpl;
import com.liceman.application.training.domain.Training;
import com.liceman.application.training.domain.enums.Status;
import com.liceman.application.training.domain.repository.TrainingRepository;
import com.liceman.application.training.infrastructure.dto.UpdateTrainingByUserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
@ExtendWith(MockitoExtension.class) // Indicates that Mockito extension is being used.
public class TrainingServiceImplTest3 {

    @InjectMocks // Creates an instance of the service. Only once.
    TrainingServiceImpl trainingServiceImpl;
    @Mock // Creates a MOCK of the TrainingRepository.
    TrainingRepository trainingRepository;

    @Test
    void userUpdateTraining_NoTraining() throws Exception{
        // Setup our mock repository
            // Parameters for the function under test
        Long id = 1L;
        UpdateTrainingByUserDTO request = new UpdateTrainingByUserDTO();
        request.setStatus(Status.APROBADA);

        // Ignore the logic of the complex method
            // Simulate the behavior of the methods used within the method under test
        BDDMockito.given(trainingRepository.findById(id)).willReturn(Optional.empty());//training is not found in the repository

        // Error handling logic
        try {
            // Execute the service call
            Training response = trainingServiceImpl.userUpdateTraining(id,request);

        } catch (IllegalStateException e) {

            // Assert the response
            Assertions.assertEquals(IllegalStateException.class, e.getClass());

        }
    }
}
