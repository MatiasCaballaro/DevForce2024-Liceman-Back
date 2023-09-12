package com.liceman.application.training.application;

import com.liceman.application.training.domain.Training;
import com.liceman.application.training.domain.TrainingEvent;
import com.liceman.application.training.domain.repository.TrainingEventRepository;
import com.liceman.application.training.domain.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TrainingEventServiceImpl implements TrainingEventService {

    private final TrainingEventRepository trainingEventRepository;  // Agrega el repositorio de eventos
    private final TrainingRepository trainingRepository;  // Agrega el repositorio de trainings
    @Override
    public List<TrainingEvent> getTrainingEvents(long id) {

        Training training = trainingRepository.findById(id).orElse(null);
        List<TrainingEvent> trainingEvents = trainingEventRepository.findAllByTraining(training);
        return trainingEvents;
    }
}
