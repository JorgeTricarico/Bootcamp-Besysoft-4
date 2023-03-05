package com.besysoft.bootcampspringboot.Exception;

public class PeliculaNotFoundException extends NotFoundException{

    public PeliculaNotFoundException(Throwable e) {
        super(e);
    }

    public PeliculaNotFoundException(String message) {
        super(message);
    }

    public PeliculaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
