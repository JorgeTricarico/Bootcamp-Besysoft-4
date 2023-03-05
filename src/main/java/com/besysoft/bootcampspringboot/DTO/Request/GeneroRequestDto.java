package com.besysoft.bootcampspringboot.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor

public class GeneroRequestDto {

    @NotBlank(message = "El nombre de genero no puede ser nulo ni estar vacio.")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "El nombre de genero puede contener letras y espacios. Regex: [A-Za-z ]")
    @Size(min = 1, max = 50, message = "El nombre del genero puede tener un de 1 a 50 caracteres.")
    private String nombre;


    public GeneroRequestDto(String nombre) {
        this.nombre = nombre;
    }
}

