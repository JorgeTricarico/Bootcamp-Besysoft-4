package com.besysoft.bootcampspringboot.services.database.implementations;

import com.besysoft.bootcampspringboot.dominio.Genero;
import com.besysoft.bootcampspringboot.respositories.database.Interfaces.IGeneroRepository;
import com.besysoft.bootcampspringboot.services.interfaces.IGeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ConditionalOnProperty(prefix = "app", name = "type-data", havingValue = "database")
@Service
public class GeneroServiceImpl implements IGeneroService {

    @Autowired
    IGeneroRepository generoRepository;
    @Override
    @Transactional(readOnly = true)
    public List<Genero> obtenerTodosLosGeneros() {
        try {
            List<Genero> listaDeGeneros = generoRepository.findAll();
            if (listaDeGeneros.isEmpty()){
                throw new NullPointerException("No existen generos en la base de datos");
            }
            return listaDeGeneros;
        }catch (RuntimeException e){
            throw new RuntimeException("Error en la conexion a la base de datos");
        }

    }

    @Override
    @Transactional(readOnly = false)
    public Genero agregarNuevoGenero(Genero genero) {
        Boolean existeGenero = generoRepository.existsGeneroByNombreIgnoreCase(genero.getNombre());

        if (existeGenero) {
            throw new RuntimeException("El nombre de genero '" + genero.getNombre() +"' ya existe.");
        }
        generoRepository.save(genero);

        return genero;//new ResponseEntity<>(genero, HttpStatus.CREATED);
    }

    @Override
    @Transactional(readOnly = false)
    public Genero actualizarGeneroPorId(Long id, Genero generoAct) {

        Boolean existeGenero = generoRepository.existsGeneroById(id);

        if (existeGenero) {
            Genero genero  = generoRepository.findById(id).get();
            genero.setNombre(generoAct.getNombre());
            return genero;
        }else{
            throw new NullPointerException("No exise ningun genero en la base de datos.");
        }
    }
}
