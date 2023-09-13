package com.liceman.application.service;

import com.liceman.application.MockBuilder;

import com.liceman.application.training.application.TrainingServiceImpl;
import com.liceman.application.training.domain.enums.Status;
import com.liceman.application.training.domain.repository.TrainingRepository;
import com.liceman.application.training.infrastructure.dto.UpdateTrainingByAdminDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.BDDMockito;

import com.liceman.application.shared.exceptions.TrainingNotExistsException;
import java.util.Optional;



import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceImplTest2 {
    @InjectMocks
    private TrainingServiceImpl trainingService;
    @Mock
    private TrainingRepository trainingRepository;
    @Test
    void adminUpdateTraining_NoTraining() throws Exception{

        //Creamos un trainingId
        Long trainingId = 1L;

        // Creamos un objeto UpdateTrainingByAdminDTO con Status APROBADA
        UpdateTrainingByAdminDTO request = MockBuilder.buildUpdateTrainingByAdminDTO(Status.APROBADA);

        //Simulamos que no se encuentra ningún objeto de training con el ID trainingId en el repositorio.
        when(trainingRepository.findById(trainingId)).thenReturn(Optional.empty());


        // Verificamos que lanzará una TrainingNotExistsException
        assertThrows(IllegalStateException.class, () -> trainingService.adminUpdateTraining(trainingId, request));
    }

    @Test
    void adminUpdateTraining_NoTraining_Exception() throws Exception{

        //Creamos un trainingId
        Long trainingId = 1L;

        // Creamos un objeto UpdateTrainingByAdminDTO con Status APROBADA
        UpdateTrainingByAdminDTO request = MockBuilder.buildUpdateTrainingByAdminDTO(Status.APROBADA);

        //Simulamos que no se encuentra ningún objeto de training con el ID trainingId en el repositorio.
        when(trainingRepository.findById(trainingId)).thenReturn(null);


        // Verificamos que lanzará una TrainingNotExistsException
        assertThrows(Exception.class, () -> trainingService.adminUpdateTraining(trainingId, request));
    }

}
