package com.besysoft.bootcampspringboot.Exception;

public class InvalidDataFormatException  extends BadRequestExcepion{

    public InvalidDataFormatException(Throwable e) {
        super(e);
    }

    public InvalidDataFormatException(String message) {
        super(message);
    }

    public InvalidDataFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
