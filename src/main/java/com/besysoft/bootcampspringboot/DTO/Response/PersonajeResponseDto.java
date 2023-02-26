package com.besysoft.bootcampspringboot.DTO.Response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PersonajeResponseDto {

    private String nombre;
    private Integer edad;
    private Double peso;
    private String historia;
    private List<PeliculaSerieTitleResponseDto> peliculasSeries;
}
