package com.besysoft.bootcampspringboot.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDTO {

//    private int estado;
//    private String mensaje;
//    private Map<String, String> detalle;
    private LocalDateTime timestamp;
    private int estado;
    private String error;
    private String path;
    private String mensaje;

}
