package com.liceman.application.training.infrastructure.dto;

import com.liceman.application.training.domain.Training;
import com.liceman.application.training.domain.enums.Status;
import com.liceman.application.user.domain.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class TrainingEventDTO {

    private Long id;
    private Training training;
    private Status currentStatus;
    private User user;
    private LocalDateTime timestamp;
}
