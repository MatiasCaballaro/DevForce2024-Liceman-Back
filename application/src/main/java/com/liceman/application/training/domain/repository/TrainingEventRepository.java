package com.liceman.application.training.domain.repository;

import com.liceman.application.training.domain.TrainingEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingEventRepository extends JpaRepository<TrainingEvent, Long> {

}