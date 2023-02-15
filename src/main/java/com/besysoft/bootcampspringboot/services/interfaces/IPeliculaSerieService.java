package com.besysoft.bootcampspringboot.services.interfaces;

import com.besysoft.bootcampspringboot.dominio.PeliculaSerie;
import org.springframework.http.ResponseEntity;

public interface IPeliculaSerieService {

    ResponseEntity<?> obtenerTodasLasPeliculas();

    ResponseEntity<?> buscarPeliculaPorTituloOGenero(String tituloOGenero);

    ResponseEntity<?> buscarPeliculaPorFecha(String desde, String hasta);
    ResponseEntity<?> buscarPeliculasPorCalificacion(Integer desde, Integer hasta);
    ResponseEntity agregarNuevaPelicula(PeliculaSerie pelicula);
    ResponseEntity actualizarPeliculaPorId(Long id, PeliculaSerie peliculaSerie);

}
