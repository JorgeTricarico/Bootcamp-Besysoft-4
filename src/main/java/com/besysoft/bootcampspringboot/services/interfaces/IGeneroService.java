package com.besysoft.bootcampspringboot.services.interfaces;

import com.besysoft.bootcampspringboot.dominio.Genero;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;



public interface IGeneroService {

    ResponseEntity<List<Genero>> obtenerTodosLosGeneros();
    ResponseEntity<?> agregarNuevoGenero(Genero genero);

    ResponseEntity actualizarGeneroPorId(Long id, Genero generoAct);


}
