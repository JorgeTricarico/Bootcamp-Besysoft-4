package com.besysoft.bootcampspringboot.DTO.Response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PeliculaResponseDto {

    private String titulo;
    private String fechaDeCreacion;
    private GeneroResponseDto nombreGenero;
}
