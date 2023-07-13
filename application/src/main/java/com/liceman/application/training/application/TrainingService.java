package com.liceman.application.training.application;

import com.liceman.application.training.domain.Training;
import com.liceman.application.training.infrastructure.dto.TrainingCreationRequestDTO;
import com.liceman.application.training.infrastructure.dto.UpdateTrainingByAdminDTO;
import com.liceman.application.training.infrastructure.dto.UpdateTrainingByMentorDTO;
import com.liceman.application.training.infrastructure.dto.UpdateTrainingByUserDTO;

import java.util.List;

public interface TrainingService {

    Training createTraining (TrainingCreationRequestDTO trainingCreationRequestDTO);

    List<Training> getTrainings ();

    Training getTrainingById (Long id) throws IllegalAccessException;

    Training mentorUpdateTraining (Long id, UpdateTrainingByMentorDTO updateTrainingByMentorDTO) throws Exception;

    Training userUpdateTraining (Long id, UpdateTrainingByUserDTO updateTrainingByUserDTO) throws Exception;

    Training adminUpdateTraining (Long id, UpdateTrainingByAdminDTO updateTrainingByAdminDTO) throws Exception;
}
