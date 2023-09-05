package com.liceman.application.training.infrastructure.dto;

import com.liceman.application.training.domain.enums.Status;
import com.liceman.application.training.domain.enums.TrainingEventRegistration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingEventDTO {
    private Long trainingEventId;
    private Long trainingId;
    private Long userId;
    private String userName;
    private Status currentStatus;
    private TrainingEventRegistration event;
    private LocalDateTime timestamp;
}
