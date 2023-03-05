package com.besysoft.bootcampspringboot.Exception;

public class PeliculaAlreadyExistsException extends BadRequestExcepion{

    public PeliculaAlreadyExistsException(Throwable e) {
        super(e);
    }

    public PeliculaAlreadyExistsException(String message) {
        super(message);
    }

    public PeliculaAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
