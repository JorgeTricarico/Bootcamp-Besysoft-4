package com.besysoft.bootcampspringboot.services.interfaces;

import com.besysoft.bootcampspringboot.DTO.Request.GeneroRequestDto;
import com.besysoft.bootcampspringboot.DTO.Response.GeneroResponseDto;

import java.util.List;



public interface IGeneroService {

    List<GeneroResponseDto> obtenerTodosLosGeneros();
    GeneroResponseDto agregarNuevoGenero(GeneroRequestDto generoRequestDto);
    GeneroResponseDto actualizarGeneroPorId(Long id, GeneroRequestDto generoRequestDto);


}
