package com.liceman.application.shared.application.mappers;

import com.liceman.application.solicitud.domain.Solicitud;
import com.liceman.application.solicitud.infrastructure.dto.SolicitudDTO;
import com.liceman.application.usuario.domain.User;
import com.liceman.application.usuario.infrastructure.dto.UserResponseDTO;
import com.liceman.application.usuario.infrastructure.dto.UserResponseWithoutSolicitudDTO;

public interface MapperUtils {

    UserResponseDTO MapperToUserDTO (User user);

    UserResponseWithoutSolicitudDTO MapperToUserWithoutSolicitudDTO (User user);

    SolicitudDTO mapperToSolicitudUserResponseDTO (Solicitud solicitud);

    
}
