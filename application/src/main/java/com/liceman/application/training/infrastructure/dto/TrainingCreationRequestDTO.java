package com.liceman.application.training.infrastructure.dto;

import com.liceman.application.user.domain.enums.Area;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingCreationRequestDTO {

    @Enumerated(EnumType.STRING)
    private Area area;

    //TODO: Ver si hacemos List<String> del comentario, quiza hacer una clase Comentario
    @Column(name = "user_comment")
    private String userComment;

    private String link;

}
