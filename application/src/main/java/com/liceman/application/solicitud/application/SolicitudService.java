package com.liceman.application.solicitud.application;

import com.liceman.application.solicitud.domain.Solicitud;
import com.liceman.application.solicitud.infrastructure.dto.SolicitudCreationRequestDTO;
import com.liceman.application.solicitud.infrastructure.dto.UpdateAdminSolicitudDTO;
import com.liceman.application.solicitud.infrastructure.dto.UpdateMentorSolicitudDTO;
import com.liceman.application.solicitud.infrastructure.dto.UpdateUserSolicitudDTO;

import java.util.List;

public interface SolicitudService {

    Solicitud createSolicitud (SolicitudCreationRequestDTO solicitudCreationRequestDTO);

    List<Solicitud> getSolicitudes ();

    Solicitud getSolicitudById (Long id) throws IllegalAccessException;

    Solicitud mentorUpdateSolicitud (Long id, UpdateMentorSolicitudDTO updateMentorSolicitudDTO) throws Exception;

    Solicitud userUpdateSolicitud (Long id, UpdateUserSolicitudDTO updateUserSolicitudDTO) throws Exception;

    Solicitud adminUpdateSolicitud (Long id, UpdateAdminSolicitudDTO updateAdminSolicitudDTO) throws Exception;
}
