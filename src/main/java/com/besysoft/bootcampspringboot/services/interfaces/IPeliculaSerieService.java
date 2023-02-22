package com.besysoft.bootcampspringboot.services.interfaces;

import com.besysoft.bootcampspringboot.dominio.PeliculaSerie;

import java.util.List;
import java.util.zip.DataFormatException;

public interface IPeliculaSerieService {

    List<PeliculaSerie> obtenerTodasLasPeliculas();
    List<PeliculaSerie> buscarPeliculaPorTituloOGenero(String tituloOGenero);
    List<PeliculaSerie> buscarPeliculaPorFecha(String desde, String hasta) throws DataFormatException;
    List<PeliculaSerie> buscarPeliculasPorCalificacion(Integer desde, Integer hasta);
    PeliculaSerie agregarNuevaPelicula(PeliculaSerie pelicula) throws DataFormatException;
    PeliculaSerie actualizarPeliculaPorId(Long id, PeliculaSerie peliculaSerie) throws DataFormatException;

}
