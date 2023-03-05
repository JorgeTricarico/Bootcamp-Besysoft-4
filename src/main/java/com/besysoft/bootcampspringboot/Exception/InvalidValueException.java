package com.besysoft.bootcampspringboot.Exception;

public class InvalidValueException extends BadRequestExcepion{

    public InvalidValueException(Throwable e) {
        super(e);
    }

    public InvalidValueException(String message, Throwable cause) {
        super(message, cause);
    }
    public InvalidValueException(String message) {
        super(message);
    }
}
