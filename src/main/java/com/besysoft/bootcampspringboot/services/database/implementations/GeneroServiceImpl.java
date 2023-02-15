package com.besysoft.bootcampspringboot.services.database.implementations;

import com.besysoft.bootcampspringboot.dominio.Genero;
import com.besysoft.bootcampspringboot.respositories.database.Interfaces.IGeneroRepository;
import com.besysoft.bootcampspringboot.services.interfaces.IGeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.*;

@ConditionalOnProperty(prefix = "app", name = "type-data", havingValue = "database")
@Service
public class GeneroServiceImpl implements IGeneroService {

    @Autowired
    IGeneroRepository generoRepository;
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<Genero>> obtenerTodosLosGeneros() {
        List<Genero> listaDeGeneros = generoRepository.findAll();
        return new ResponseEntity<>(listaDeGeneros, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = false)
    public ResponseEntity<?> agregarNuevoGenero(Genero genero) {

        Boolean existeGenero = generoRepository.existsGeneroByNombreIgnoreCase(genero.getNombre());

        if (existeGenero) {
            return badResquest("El nombre de genero '%s' ya existe", genero.getNombre());
        }

        if (genero.getNombre().isBlank()) {
            return badResquest("El nombre de genero no puede ser nulo");
        }

        //Long cantidadDeGeneros = Long.valueOf(generoRepository.findAll().size());
        //genero.setId(cantidadDeGeneros + 1);
        generoRepository.save(genero);

        return new ResponseEntity<>(genero, HttpStatus.CREATED);
    }

    @Override
    @Transactional(readOnly = false)
    public ResponseEntity actualizarGeneroPorId(Long id, Genero generoAct) {

        Boolean existeGenero = generoRepository.existsGeneroById(id);

        if (existeGenero) {

            if (generoAct.getNombre().isBlank()) {
                return badResquest("El nombre no puede ser nulo o estar vacio");
            }

            Genero genero  = generoRepository.findById(id).get();


            genero.setNombre(generoAct.getNombre());


            return new ResponseEntity(genero, HttpStatus.OK);
        }else{
            return notFound("El id %s ingresado no existe", id.toString());
        }
    }
}
