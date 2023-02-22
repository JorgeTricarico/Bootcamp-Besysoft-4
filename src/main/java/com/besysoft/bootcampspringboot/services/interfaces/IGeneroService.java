package com.besysoft.bootcampspringboot.services.interfaces;

import com.besysoft.bootcampspringboot.dominio.Genero;
import org.springframework.http.ResponseEntity;

import java.util.List;



public interface IGeneroService {

    List<Genero> obtenerTodosLosGeneros();
    Genero agregarNuevoGenero(Genero genero);

    ResponseEntity actualizarGeneroPorId(Long id, Genero generoAct);


}
