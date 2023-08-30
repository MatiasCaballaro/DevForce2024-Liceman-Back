package com.liceman.application.shared.exceptions;

import java.io.IOException;

public class NotValidImageFormatException extends IOException {
    @Override
    public String getMessage () {
        return "invalid image format";
    }
}
