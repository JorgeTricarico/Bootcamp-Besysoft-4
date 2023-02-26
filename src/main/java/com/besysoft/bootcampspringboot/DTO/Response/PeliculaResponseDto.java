package com.besysoft.bootcampspringboot.DTO.Response;

import com.besysoft.bootcampspringboot.dominio.Personaje;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PeliculaResponseDto {

    private String titulo;
    private String fechaDeCreacion;
    private GeneroResponseDto nombreGenero;
    private List<PersonajeNameResponseDto> personajesAsociados;
}
