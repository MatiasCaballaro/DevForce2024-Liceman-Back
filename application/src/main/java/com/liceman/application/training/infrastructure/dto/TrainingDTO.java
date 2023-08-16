package com.liceman.application.training.infrastructure.dto;

import com.liceman.application.training.domain.enums.Status;
import com.liceman.application.user.domain.enums.Area;
import com.liceman.application.user.infrastructure.dto.UserResponseWithoutTrainingDTO;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDTO {

    private Long id;

    @Enumerated(EnumType.STRING)
    private Area area;

    private String title;

    private LocalDateTime creationDate;

    private LocalDateTime approvedDate;

    private LocalDateTime endDate;

    private Integer days;

    private Status status;

    private List<CommentDTO> comments;

    private String link;

    private UserResponseWithoutTrainingDTO userId;

    private UserResponseWithoutTrainingDTO mentorId;

    private UserResponseWithoutTrainingDTO adminId;

}
