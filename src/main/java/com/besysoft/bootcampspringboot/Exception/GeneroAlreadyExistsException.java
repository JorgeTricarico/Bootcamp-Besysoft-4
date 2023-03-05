package com.besysoft.bootcampspringboot.Exception;

public class GeneroAlreadyExistsException extends BadRequestExcepion{

    public GeneroAlreadyExistsException(Throwable e) {
        super(e);
    }

    public GeneroAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeneroAlreadyExistsException(String message) {
        super(message);
    }
}
