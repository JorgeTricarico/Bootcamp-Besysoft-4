package com.besysoft.bootcampspringboot.services.interfaces;

import com.besysoft.bootcampspringboot.dominio.Genero;

import java.util.List;



public interface IGeneroService {

    List<Genero> obtenerTodosLosGeneros();
    Genero agregarNuevoGenero(Genero genero);

    Genero actualizarGeneroPorId(Long id, Genero generoAct);


}
