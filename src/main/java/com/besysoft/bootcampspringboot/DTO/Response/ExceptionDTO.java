package com.besysoft.bootcampspringboot.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor

public class ExceptionDTO {

//    private int estado;
//    private String mensaje;
//    private Map<String, String> detalle;
    private LocalDateTime timestamp;
    private String error;
    private String path;
    private String mensaje;

    public ExceptionDTO(HttpStatus error, String path, String mensaje) {
        this.timestamp = LocalDateTime.now();
        this.error = error.toString();
        this.path = path;
        this.mensaje = mensaje;
    }
}
