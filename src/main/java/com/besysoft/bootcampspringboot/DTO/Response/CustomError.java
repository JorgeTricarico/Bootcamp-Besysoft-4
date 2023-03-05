package com.besysoft.bootcampspringboot.DTO.Response;

public class CustomError extends Error {
    //private String additionalField;



    public CustomError(String message) {
        super(message);

    }

    public CustomError(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomError(Throwable cause) {
        super(cause);
    }

    public CustomError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CustomError() {
    }
}
