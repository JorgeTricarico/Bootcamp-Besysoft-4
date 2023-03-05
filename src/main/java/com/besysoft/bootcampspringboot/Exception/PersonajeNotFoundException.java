package com.besysoft.bootcampspringboot.Exception;

public class PersonajeNotFoundException extends NotFoundException{

    public PersonajeNotFoundException(Throwable e) {
        super(e);
    }

    public PersonajeNotFoundException(String message) {
        super(message);
    }

    public PersonajeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
