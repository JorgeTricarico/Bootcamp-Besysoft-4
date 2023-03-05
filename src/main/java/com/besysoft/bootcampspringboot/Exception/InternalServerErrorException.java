package com.besysoft.bootcampspringboot.Exception;

public class InternalServerErrorException extends RuntimeException{

    public InternalServerErrorException(Throwable e){
        super(e);
    }
    public InternalServerErrorException(String message) {
        super(message);
    }
    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
