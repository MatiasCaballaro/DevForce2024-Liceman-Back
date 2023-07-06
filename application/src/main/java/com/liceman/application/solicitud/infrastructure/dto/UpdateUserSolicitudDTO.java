package com.liceman.application.solicitud.infrastructure.dto;

import com.liceman.application.solicitud.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserSolicitudDTO {
    private Status status;
}
