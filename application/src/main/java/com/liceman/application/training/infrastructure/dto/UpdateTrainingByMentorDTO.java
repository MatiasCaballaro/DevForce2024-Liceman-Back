package com.liceman.application.training.infrastructure.dto;

import com.liceman.application.training.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTrainingByMentorDTO {

    private Integer days;
    private String link;
    private String comment;
    private Status status;

}
