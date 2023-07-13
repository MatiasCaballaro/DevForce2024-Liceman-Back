package com.liceman.application.training.infrastructure.dto;

import com.liceman.application.training.domain.enums.Status;
import com.liceman.application.user.domain.enums.Area;
import com.liceman.application.user.infrastructure.dto.UserResponseWithoutTrainingDTO;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDTO {

    private Long id;

    @Enumerated(EnumType.STRING)
    private Area area;

    private LocalDateTime creationDate;

    private LocalDateTime approvedDate;

    private LocalDateTime endDate;

    private Integer days;

    private Status status;


    @Column(name = "user_comment")
    private String userComment;

    @Column(name = "mentor_comment")
    private String mentorComment;

    private String link;

    private UserResponseWithoutTrainingDTO userId;

    private UserResponseWithoutTrainingDTO mentorId;

    private UserResponseWithoutTrainingDTO adminId;

}
