package com.besysoft.bootcampspringboot.services.interfaces;

import com.besysoft.bootcampspringboot.dominio.Personaje;
import org.springframework.http.ResponseEntity;

public interface IPersonajeService {

     ResponseEntity<?> obtenerTodosLosPersonajes();
     ResponseEntity<?> buscarPorEdadONombre(String dato);
     ResponseEntity<?> buscarPersonajePorNombre(String nombre);
     ResponseEntity<?> buscarPersonajesPorEdad(Integer edad);
     ResponseEntity<?> buscarPersonajePorRangoDeEdad(Integer desde, Integer hasta);
     ResponseEntity agregarNuevoPersonaje(Personaje personaje);
     ResponseEntity actualizarPersonajePorId(Long id, Personaje personajeAct);

}
