package com.besysoft.bootcampspringboot.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
public class ValidExceptionResponse extends Error {

    private String mensaje;
    private Map<String, String> detalle;


    public ValidExceptionResponse(String mensaje, Map<String, String> detalle) {
        this.mensaje = mensaje;
        this.detalle = detalle;
    }

    public ValidExceptionResponse(String message, String mensaje, Map<String, String> detalle) {
        super(message);
        this.mensaje = mensaje;
        this.detalle = detalle;
    }

    public ValidExceptionResponse(String message, Throwable cause, String mensaje, Map<String, String> detalle) {
        super(message, cause);
        this.mensaje = mensaje;
        this.detalle = detalle;
    }

    public ValidExceptionResponse(Throwable cause, String mensaje, Map<String, String> detalle) {
        super(cause);
        this.mensaje = mensaje;
        this.detalle = detalle;
    }

    public ValidExceptionResponse(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String mensaje, Map<String, String> detalle) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.mensaje = mensaje;
        this.detalle = detalle;
    }
    /*private LocalDateTime timestamp;
    private int estado;
    private String error;
    private String path;
    private String mensaje;
    private Map<String, String> detalle;*/


}
