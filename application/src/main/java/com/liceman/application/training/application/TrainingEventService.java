package com.liceman.application.training.application;

import com.liceman.application.training.domain.Training;
import com.liceman.application.training.domain.TrainingEvent;

import java.util.List;

public interface TrainingEventService {

    List<TrainingEvent> getTrainingEvents (long id);
}
