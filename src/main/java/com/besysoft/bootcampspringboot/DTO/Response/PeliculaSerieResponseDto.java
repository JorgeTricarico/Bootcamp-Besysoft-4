package com.besysoft.bootcampspringboot.DTO.Response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PeliculaSerieResponseDto {

    private String titulo;
    private String fechaDeCreacion;
    private GeneroResponseDto nombreGenero;
    private List<PersonajeNameResponseDto> personajesAsociados;
}
