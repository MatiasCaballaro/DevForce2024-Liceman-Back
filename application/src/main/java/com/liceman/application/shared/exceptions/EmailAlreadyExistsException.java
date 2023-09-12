package com.liceman.application.shared.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{
    @Override
    public String getMessage () {
        return "This email can't be used";
    }
}
