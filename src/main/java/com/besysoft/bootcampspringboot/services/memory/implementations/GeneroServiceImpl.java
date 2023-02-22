package com.besysoft.bootcampspringboot.services.memory.implementations;

import com.besysoft.bootcampspringboot.dominio.Genero;
import com.besysoft.bootcampspringboot.respositories.memory.interfaces.IGeneroRepository;
import com.besysoft.bootcampspringboot.services.interfaces.IGeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.badResquest;
import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.headers;


@Service
@ConditionalOnProperty(prefix = "app", name = "type-data", havingValue = "memory")
public class GeneroServiceImpl implements IGeneroService {

    @Autowired
    IGeneroRepository generoRepository;

    @Override
    public Genero agregarNuevoGenero(Genero genero) {
        Optional<Genero> optinalGenero = generoRepository.obtenerTodosLosGeneros()
                .stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(genero.getNombre())).findAny();

        if (optinalGenero.isPresent()) {
            throw new RuntimeException("El nombre de genero '" + genero.getNombre() +"' ya existe.");
        }

        Long cantidadDeGeneros = Long.valueOf(generoRepository.obtenerTodosLosGeneros().size());

        genero.setId(cantidadDeGeneros + 1);
        generoRepository.agregarNuevoGenero(genero);

        return genero;
    }

    @Override
    public List<Genero> obtenerTodosLosGeneros() {
        List<Genero> generos = generoRepository.obtenerTodosLosGeneros();
        return generos;
    }

    @Override
    public Genero actualizarGeneroPorId(Long id, Genero generoAct) {
        Optional<Genero> optionalGenero = generoRepository.buscarGeneroPorId(id);

        if (optionalGenero.isPresent()) {
            Genero genero = optionalGenero.get();

            if (generoAct.getNombre().isBlank()) {
                throw new IllegalArgumentException("El nombre no puede ser nulo o estar vacio");
            }
            /*if (generoAct.getPeliculasSeries() == null || generoAct.getPeliculaSerie().size()<1){
                return badResquest("Las peliculas sociadas no pueden estar vacias o ser nulas");
            }*/

            genero.setNombre(generoAct.getNombre());
            /*genero.setPeliculaSerie(generoAct.getPeliculaSerie());*/

            return genero;
        }else{
            throw new IllegalArgumentException("El id ingresado no existe");
        }
    }
}
