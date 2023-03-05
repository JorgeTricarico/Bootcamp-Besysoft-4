package com.besysoft.bootcampspringboot.Exception;

public class GeneroNotFoundException extends NotFoundException{

    public GeneroNotFoundException(Throwable e) {
        super(e);
    }

    public GeneroNotFoundException(String message) {
        super(message);
    }

    public GeneroNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
