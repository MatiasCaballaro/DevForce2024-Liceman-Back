package com.liceman.application.training.domain;

import com.liceman.application.training.domain.enums.Status;
import com.liceman.application.training.domain.enums.TrainingEventRegistration;
import com.liceman.application.user.domain.User;
import com.liceman.application.user.domain.enums.Area;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "training_events")
public class TrainingEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_id")
    private Training training;

    @Enumerated(EnumType.STRING)
    private Status currentStatus;

    @Enumerated(EnumType.STRING)
    private TrainingEventRegistration event;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime timestamp;
}