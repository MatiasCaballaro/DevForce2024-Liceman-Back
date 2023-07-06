package com.liceman.application.shared.exceptions;

public class SolicitudNotExistsException extends RuntimeException{
    @Override
    public String getMessage () {
        return "Solicitud no existe";
    }
}
