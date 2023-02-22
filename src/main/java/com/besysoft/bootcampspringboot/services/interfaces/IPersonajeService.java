package com.besysoft.bootcampspringboot.services.interfaces;

import com.besysoft.bootcampspringboot.dominio.Personaje;

import java.util.List;

public interface IPersonajeService {

     List<Personaje> obtenerTodosLosPersonajes();
     List<Personaje> buscarPorEdadONombre(String dato);
     List<Personaje> buscarPersonajePorNombre(String nombre);
     List<Personaje> buscarPersonajesPorEdad(Integer edad);
     List<Personaje> buscarPersonajePorRangoDeEdad(Integer desde, Integer hasta);
     Personaje agregarNuevoPersonaje(Personaje personaje);
     Personaje actualizarPersonajePorId(Long id, Personaje personajeAct);

}
