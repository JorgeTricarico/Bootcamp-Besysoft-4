package com.besysoft.bootcampspringboot.utilidades;

import com.besysoft.bootcampspringboot.Exception.InvalidDataFormatException;
import com.besysoft.bootcampspringboot.dominio.PeliculaSerie;
import com.besysoft.bootcampspringboot.dominio.Personaje;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

@Slf4j
public class Validaciones {

    public static void validarNombre(String nombreObjeto, String nombre) {
        validarIsBlank(nombreObjeto, nombre);
        validarLetras(nombreObjeto, nombre);
    }

    public static void validarTitulo(String nombreObjeto, String titulo) {
        validarIsBlank(nombreObjeto, titulo);
        validarLetrasYNumeros(nombreObjeto, titulo);
    }

    public static void validarPelicula(String nombreObjeto, PeliculaSerie pelicula) throws DataFormatException {
        validarTitulo(nombreObjeto, pelicula.getTitulo());
        validarFechaDeCreacion(pelicula);
        validarCalificacion(pelicula.getCalificacion());

    }

    public static void validarPelicula(PeliculaSerie pelicula) throws DataFormatException {
        validarTitulo("pelicula o serie", pelicula.getTitulo());
        validarFechaDeCreacion(pelicula);
        validarCalificacion(pelicula.getCalificacion());

    }

    public static void validarCalificacion(Integer calificacion) {
        if (calificacion == null || calificacion > 5 || calificacion < 1) {
            throw new InvalidDataFormatException("La calificacion no puede ser nula y tiene que estar entre 1 y 5");
        }
    }

    public static void validarLetrasYNumeros(String nombreObjeto, String nombre) {
        validarIsBlank(nombreObjeto, nombre);
        Boolean sonSoloLetrasYNumeros = nombre.matches("^[a-zA-Z0-9 ]+$");
        if (!sonSoloLetrasYNumeros) {
            throw new InvalidDataFormatException("El nombre de " + nombreObjeto + " debe contener solo letras y/o numeros positivos");
        }
    }

    public static void validarLetras(String nombreObjeto, String nombre) {
        Boolean sonSoloLetrasNumeros = nombre.matches("^[a-zA-Z ]+$");
        if (!sonSoloLetrasNumeros) {
            throw new InvalidDataFormatException("El nombre de " + nombreObjeto + " debe contener solo letras.");
        }
    }

    public static void validarNumeros(String numero) {
        Boolean sonSoloNumeros = numero.matches("^[0-9]+$");
        if (!sonSoloNumeros) {
            throw new InvalidDataFormatException("El desde o hasta debe ser un numero del 1 al 5");
        }
    }

    public static void validarCalificacionPorRango(Integer desde, Integer hasta) {
        if (desde == null || hasta == null) {
            throw new InvalidDataFormatException("Las calificaciones no pueden ser nulas");
        }
        if (desde < 1 || desde > 5 || hasta < 1 || hasta > 5) {
            throw new InvalidDataFormatException("Las calificaciones debe ser del 1 al 5");
        }
        if (!(desde <= hasta)) {
            throw new InvalidDataFormatException("Las calificaciones desde y hasta deben ser iguales o en orden ascendente");
        }
    }

    public static void validarFecha(String desde, String hasta){
        validarFechaPorRango(desde, hasta);
    }

    public static void validarFechaDeCreacion(PeliculaSerie pelicula) throws DataFormatException {
        if (pelicula.getFechaDeCreacion() == null) {
            throw new InvalidDataFormatException("La fecha de creacion no puede ser nula o estar vacia.");
        }
        if (pelicula.getFechaDeCreacion().isAfter(LocalDate.now())) {
            throw new InvalidDataFormatException("La fecha no puede ser del futuro.");
        }
    }


    public static void validarPersonaje(Personaje personaje) {
        validarNombre("personaje", personaje.getNombre());
        if (personaje.getEdad() < 0 || personaje.getEdad() == null) {
            throw new IllegalArgumentException("La edad tiene que ser un numero mayor o igual a 0 y no puede ser nula o estar vacia");
        }
        if (personaje.getPeso() < 0 || personaje.getPeso() == null) {
            throw new IllegalArgumentException("El peso no puede ser menor a 0 o sernulo");
        }
        if (personaje.getHistoria().isBlank()) {
            throw new IllegalArgumentException("La historia no puede ser nula o estar vacia");
        }
    }

    public static void validarFechaPorRango(String desde, String hasta) {
        if (desde.compareTo(hasta) > 0) {
            throw new InvalidDataFormatException("Rango de fecha inválido.");
        }
    }

    public static void validarIsBlank(String nombreObjeto, String nombre) {
        if (nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de " + nombreObjeto + " no puede ser nulo o estar vacio.");
        }
    }

    public static void validarEdadPorRango(Integer desde, Integer hasta) {
        if (desde == null || hasta == null) {
            throw new IllegalArgumentException("La edad no pueden ser nula");
        }
        if (desde < 0 || hasta < 0) {
            throw new IllegalArgumentException("Las edades debe ser mayores a 0");
        }
        if (!(desde <= hasta)) {
            throw new IllegalArgumentException("Las edades desde y hasta deben ser iguales o en orden ascendente");
        }
    }


    public static void logValidation(Throwable e){
        Map<String, Object> mensajeBody = new HashMap<>();
        mensajeBody.put("Cause ", e.getStackTrace()[0].toString()
                + "\n" + e.getStackTrace()[1].toString() + "\n");
        mensajeBody.put("Message ", e.getMessage() + "\n");
        mensajeBody.put("Exception ", e.getClass().getSimpleName() + "\n");
        log.info("Se lanzo una validacion personalizada: \n" + mensajeBody);
    }

    public static void logPersonalException(Throwable e){
        Map<String, Object> mensajeBody = new HashMap<>();
        mensajeBody.put("Cause ", e.getStackTrace()[0].toString()
                + "\n" + e.getStackTrace()[1].toString() + "\n");
        mensajeBody.put("Message ", e.getMessage() + "\n");
        mensajeBody.put("Exception ", e.getClass().getSimpleName() + "\n");
        log.info("Se lanzo una excepcion personalizada: \n" + mensajeBody);
    }

    public static void logUnexpected(Throwable e){
        Map<String, Object> mensajeBody = new HashMap<>();
        mensajeBody.put("Cause ", e.getStackTrace()[0].toString()
                + "\n" + e.getStackTrace()[1].toString() + "\n");
        mensajeBody.put("Message ", e.getMessage() + "\n");
        mensajeBody.put("Exception ", e.getClass().getSimpleName() + "\n");
        log.warn("Se lanzo una excepcion inesperada: \n" + mensajeBody);
    }

}
