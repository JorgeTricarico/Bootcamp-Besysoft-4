package com.besysoft.bootcampspringboot.Exception;

public class PersonajeAlreadyExistsException extends BadRequestExcepion{

    public PersonajeAlreadyExistsException(Throwable e) {
        super(e);
    }

    public PersonajeAlreadyExistsException(String message) {
        super(message);
    }

    public PersonajeAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
