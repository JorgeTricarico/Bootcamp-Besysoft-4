package com.besysoft.bootcampspringboot.controlador;

import com.besysoft.bootcampspringboot.dominio.PeliculaSerie;

import com.besysoft.bootcampspringboot.services.interfaces.IPeliculaSerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/peliculas")
public class PeliculaSerieControlador {

    @Autowired
    IPeliculaSerieService peliculaService;

    @GetMapping
    public ResponseEntity<?> mostrarTodasLasPeliculas() {
        return peliculaService.obtenerTodasLasPeliculas();
    }

    @GetMapping("/{tituloOGenero}")
    public ResponseEntity<?> buscarPorTitulo(@PathVariable String tituloOGenero) {
        return peliculaService.buscarPeliculaPorTituloOGenero(tituloOGenero);
    }

    @GetMapping("/fechas")
    public ResponseEntity<?> buscarPorFecha(@RequestParam String desde, @RequestParam String hasta){
        return peliculaService.buscarPeliculaPorFecha(desde, hasta);
    }

    @GetMapping("/calificacion")
    public ResponseEntity<?> buscarPorCalificacion(@RequestParam Integer desde, @RequestParam Integer hasta){
        return peliculaService.buscarPeliculasPorCalificacion(desde, hasta);
    }

    @PostMapping
    public ResponseEntity<?> agregarPelicula(@RequestBody PeliculaSerie pelicula){
        return peliculaService.agregarNuevaPelicula(pelicula);
    }

    @PutMapping("/{id}")
    public ResponseEntity actualizarPelicula (@PathVariable Long id, @RequestBody PeliculaSerie peliculaSerie){
        return peliculaService.actualizarPeliculaPorId(id, peliculaSerie);
    }




}