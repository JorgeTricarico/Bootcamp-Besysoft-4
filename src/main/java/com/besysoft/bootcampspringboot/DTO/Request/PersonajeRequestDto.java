package com.besysoft.bootcampspringboot.DTO.Request;

import javax.validation.constraints.*;
import java.util.List;

public class PersonajeRequestDto {

    @NotBlank(message = "El nombre del personaje no puede ser nulo ni estar vacio.")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "El nombre del personaje debe contener solo letras. Regex: [A-Za-z ].")
    @Size(min = 1, max = 50,message = "El nombre del personaje debe tener un de 1 a 50 caracteres.")
    private String nombre;

    @Max(message = "La edad no puede ser mayor a 127.", value = 127)
    @NotNull(message = "La edad no puede ser nula.")
    private Integer edad;

    @DecimalMin(message = "El peso no puede ser menor a 0.", value = "0", inclusive = true)
    @NotNull(message = "El peso no puede ser nulo.")
    private Double peso;

    @Size(message = "La historia no puede contener mas de 255 carácteres.", max = 255)
    @NotBlank(message = "La historia no puede ser nula o estar vacía.")
    private String historia;

    private List<PeliculaSerieRequestDto> peliculasSeries;
}
