package com.besysoft.bootcampspringboot.utilidades;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Fechas {

    public static LocalDate formatear(String fecha) {
        DateTimeFormatter formateador;
        try {
            formateador = new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .append(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    .toFormatter();
            return LocalDate.parse(fecha, formateador);
        }catch (RuntimeException e){
            throw new IllegalArgumentException("La fecha ingresada debe tener el formato dd-MM-yyyy");//throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }


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
