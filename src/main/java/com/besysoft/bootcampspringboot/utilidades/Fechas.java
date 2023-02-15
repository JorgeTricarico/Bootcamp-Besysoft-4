package com.besysoft.bootcampspringboot.utilidades;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.badResquest;

public class Fechas {

    public static LocalDate formatear(String fecha) {
        DateTimeFormatter formateador;
        try {
            formateador = new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .append(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    .toFormatter();
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

        return LocalDate.parse(fecha, formateador);
    }

    /*public static ResponseEntity validarFecha(LocalDate fechaDeCreacion){

        if(fechaDeCreacion == null){
            return badResquest("La calificacion no puede ser nula y tiene que estar entre 1 y 5");
            //throw new ResponseStatusException(HttpStatus.CONFLICT,"La fecha no puede ser nula.");
            //throw new IllegalArgumentException("La fecha no puede ser nula.");
        }

        if(fechaDeCreacion.isAfter(LocalDate.now())){
            return badResquest("La fecha no puede ser del futuro.");
        }

    }*/

}
