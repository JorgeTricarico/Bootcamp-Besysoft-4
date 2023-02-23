package com.besysoft.bootcampspringboot.controlador;

import com.besysoft.bootcampspringboot.dominio.PeliculaSerie;

import com.besysoft.bootcampspringboot.services.interfaces.IPeliculaSerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.zip.DataFormatException;

import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.*;
import static com.besysoft.bootcampspringboot.utilidades.Validaciones.*;


@RestController
@RequestMapping("/peliculas")
public class PeliculaSerieControlador {

    @Autowired
    IPeliculaSerieService peliculaService;

    @GetMapping
    public ResponseEntity<?> mostrarTodasLasPeliculas() {
        try {
            return ok(peliculaService.obtenerTodasLasPeliculas());
        }catch (NullPointerException e){
            return notFound(e.getMessage());
        }catch (RuntimeException e){
            return internalServerError(e.getMessage());
        }
    }

    @GetMapping("/{tituloOGenero}")
    public ResponseEntity<?> buscarPorTituloOGenero(@PathVariable String tituloOGenero) {
        try {
            validarTitulo("pelicula, serie o Genero", tituloOGenero);
            List<PeliculaSerie> peliculaSeries = peliculaService.buscarPeliculaPorTituloOGenero(tituloOGenero);
            return ok(peliculaSeries);
        }catch (IllegalArgumentException e){
            return badResquest(e.getMessage());
        }catch (NullPointerException e){
            return notFound(e.getMessage());
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/fechas")
    public ResponseEntity<?> buscarPorFecha(@RequestParam String desde, @RequestParam String hasta) {
        try {
            validarFecha(desde, hasta);
            return ok(peliculaService.buscarPeliculaPorFecha(desde, hasta));
        }catch (IllegalArgumentException e){
            return badResquest(e.getMessage());
        }catch (NullPointerException e){
            return notFound(e.getMessage());
        }catch (DataFormatException e){
            return badResquest(e.getMessage());
        }catch (RuntimeException e){
            return internalServerError(e.getMessage());
        }
    }

    @GetMapping("/calificacion")
    public ResponseEntity<?> buscarPorCalificacion(@RequestParam Integer desde, @RequestParam Integer hasta){
        try {
            validarCalificacionPorRango(desde, hasta);
            return ok(peliculaService.buscarPeliculasPorCalificacion(desde, hasta));
        } catch (IllegalArgumentException e) {
            return badResquest(e.getMessage());
        }catch (NullPointerException e) {
            return notFound(e.getMessage());
        }catch (RuntimeException e){
            return internalServerError(e.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity<?> agregarPelicula(@RequestBody PeliculaSerie pelicula){
        try {
            validarPelicula("pelicula o serie", pelicula);
            return created(peliculaService.agregarNuevaPelicula(pelicula));
        }catch (IllegalArgumentException e){
            return badResquest(e.getMessage());
        } catch (DataFormatException e) {
            return badResquest(e.getMessage());
        }catch (RuntimeException e){
            return internalServerError(e.toString());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity actualizarPelicula (@PathVariable Long id, @RequestBody PeliculaSerie pelicula){
        try {
            validarPelicula(pelicula);
            return created(peliculaService.actualizarPeliculaPorId(id, pelicula));
        }catch (NullPointerException e){
            return notFound(e.getMessage());
        }catch (IllegalArgumentException e){
            return badResquest(e.getMessage());
        } catch (DataFormatException e) {
            return badResquest(e.getMessage());
        }catch (RuntimeException e){
            return internalServerError(e.getMessage());
        }
    }




}