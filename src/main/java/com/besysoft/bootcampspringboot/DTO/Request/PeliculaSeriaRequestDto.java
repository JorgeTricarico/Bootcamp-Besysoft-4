package com.besysoft.bootcampspringboot.DTO.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class PeliculaSeriaRequestDto {

    @NotBlank(message = "El titulo de pelicula o serie no puede ser nulo o estar vacio.")
    @Pattern(regexp = "^[A-Za-z0-9 ]+$", message = "El titulo solo puede contener letras, numeros y espacios. Regex: [a-zA-Z0-9 ]")
    @Size(min = 1, max = 50, message = "El titulo de la pelicula o serie debe tener un de 1 a 50 caracteres.")
    private String titulo;

    @NotNull(message = "La fecha de creación no puede ser nula.")
    //@JsonFormat(pattern = "dd-MM-yyyy")
    private String fechaDeCreacion;

    @Min(message = "La calificación debe ser del 1 al 5.", value = 1)
    @Max(message = "La calificación debe ser del 1 al 5.", value = 5)
    @NotNull(message = "La calificación no puede ser nula.")
    private Integer calificacion;

    @NotBlank(message = "El nombre de genero no puede ser nulo ni estar vacio.")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "El nombre de genero puede contener letras y espacios. Regex: [A-Za-z ]")
    @Size(min = 1, max = 50, message = "El nombre del genero puede tener un de 1 a 50 caracteres.")
    private GeneroRequestDto nombreGenero;
}
