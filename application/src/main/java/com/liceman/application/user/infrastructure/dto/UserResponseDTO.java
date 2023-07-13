package com.liceman.application.user.infrastructure.dto;

import com.liceman.application.training.infrastructure.dto.TrainingDTO;
import com.liceman.application.user.domain.enums.Area;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String role;
    private String phone;
    private Boolean hasTeams;
    private Area area;
    private List<TrainingDTO> trainings;

}
