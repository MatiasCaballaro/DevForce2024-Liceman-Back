package com.liceman.application.shared.exceptions;

public class TrainingNotExistsException extends RuntimeException{
    @Override
    public String getMessage () {
        return "Training no existe";
    }
}
