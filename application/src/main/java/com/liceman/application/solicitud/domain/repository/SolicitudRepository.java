package com.liceman.application.solicitud.domain.repository;

import com.liceman.application.solicitud.domain.Solicitud;
import com.liceman.application.usuario.domain.User;
import com.liceman.application.usuario.domain.enums.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    List<Solicitud> findAllByUserIdIs(User user);
    List<Solicitud> findAllByArea (Area area);

}
