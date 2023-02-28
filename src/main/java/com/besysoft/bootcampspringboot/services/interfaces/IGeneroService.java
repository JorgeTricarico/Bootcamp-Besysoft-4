package com.besysoft.bootcampspringboot.services.interfaces;

import com.besysoft.bootcampspringboot.DTO.Request.GeneroRequestDto;
import com.besysoft.bootcampspringboot.DTO.Response.GeneroResponseDto;

import java.util.List;



public interface IGeneroService {

    List<GeneroResponseDto> obtenerTodosLosGeneros();
    GeneroRequestDto agregarNuevoGenero(GeneroRequestDto generoRequestDto);
    GeneroRequestDto actualizarGeneroPorId(Long id, GeneroRequestDto generoRequestDto);


}
