package com.besysoft.bootcampspringboot.Exception;

public class BadRequestExcepion extends RuntimeException{
    public BadRequestExcepion(Throwable e){
        super(e);
    }
    public BadRequestExcepion(String message) {
        super(message);
    }
    public BadRequestExcepion(String message, Throwable cause) {
        super(message, cause);
    }

}
