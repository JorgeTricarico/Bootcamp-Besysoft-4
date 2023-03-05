package com.besysoft.bootcampspringboot.services.interfaces;

import com.besysoft.bootcampspringboot.DTO.Request.PeliculaSerieRequestDto;
import com.besysoft.bootcampspringboot.DTO.Response.PeliculaSerieResponseDto;
import com.besysoft.bootcampspringboot.Exception.InvalidValueException;

import java.util.List;
import java.util.zip.DataFormatException;

public interface IPeliculaSerieService {

    List<PeliculaSerieResponseDto> obtenerTodasLasPeliculas();
    List<PeliculaSerieResponseDto> buscarPeliculaPorTituloOGenero(String tituloOGenero);
    List<PeliculaSerieResponseDto> buscarPeliculaPorFecha(String desde, String hasta) throws InvalidValueException;
    List<PeliculaSerieResponseDto> buscarPeliculasPorCalificacion(Integer desde, Integer hasta);
    PeliculaSerieResponseDto agregarNuevaPelicula(PeliculaSerieRequestDto peliculaRequestDto);
    PeliculaSerieResponseDto actualizarPeliculaPorId(Long id, PeliculaSerieRequestDto peliculaRequestDto);

}
