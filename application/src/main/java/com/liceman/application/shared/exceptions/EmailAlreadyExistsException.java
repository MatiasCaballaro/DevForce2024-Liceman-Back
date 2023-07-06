package com.liceman.application.shared.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{
    @Override
    public String getMessage () {
        return "El email que eliges no puede ser utilizado.";
    }
}
