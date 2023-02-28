package com.besysoft.bootcampspringboot.services.interfaces;

import com.besysoft.bootcampspringboot.DTO.Request.PersonajeRequestDto;
import com.besysoft.bootcampspringboot.DTO.Response.PersonajeResponseDto;

import java.util.List;

public interface IPersonajeService {

     List<PersonajeResponseDto> obtenerTodosLosPersonajes();
     List<PersonajeResponseDto> buscarPorEdadONombre(String dato);
     List<PersonajeResponseDto> buscarPersonajePorNombre(String nombre);
     List<PersonajeResponseDto> buscarPersonajesPorEdad(Integer edad);
     List<PersonajeResponseDto> buscarPersonajePorRangoDeEdad(Integer desde, Integer hasta);
     PersonajeResponseDto agregarNuevoPersonaje(PersonajeRequestDto personajeRequestDto);
     PersonajeResponseDto actualizarPersonajePorId(Long id, PersonajeRequestDto personajeRequestDto);

}
