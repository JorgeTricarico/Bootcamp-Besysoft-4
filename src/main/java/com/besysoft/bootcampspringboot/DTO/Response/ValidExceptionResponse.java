package com.besysoft.bootcampspringboot.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
public class ValidExceptionResponse {

    private LocalDateTime timestamp;
    private int estado;
    private String error;
    private String path;
    private String mensaje;
    private Map<String, String> detalle;

    public ValidExceptionResponse(HttpStatus error, String path, String mensaje, Map<String, String> detalle) {
        this.timestamp = LocalDateTime.now();
        this.estado = error.value();
        this.error = error.toString();
        this.path = path;
        this.mensaje = mensaje;
        this.detalle = detalle;
    }


}
