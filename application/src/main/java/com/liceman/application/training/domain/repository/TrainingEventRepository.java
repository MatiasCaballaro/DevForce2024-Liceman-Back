package com.liceman.application.training.domain.repository;

import com.liceman.application.training.domain.Training;
import com.liceman.application.training.domain.TrainingEvent;
import com.liceman.application.user.domain.enums.Area;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingEventRepository extends JpaRepository<TrainingEvent, Long> {
    List<TrainingEvent> findAllByTraining (Training training);
}