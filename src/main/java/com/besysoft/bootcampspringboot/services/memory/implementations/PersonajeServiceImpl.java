package com.besysoft.bootcampspringboot.services.memory.implementations;

import com.besysoft.bootcampspringboot.dominio.Personaje;
import com.besysoft.bootcampspringboot.respositories.memory.interfaces.IPersonajeRepository;
import com.besysoft.bootcampspringboot.services.interfaces.IPersonajeService;
import com.besysoft.bootcampspringboot.utilidades.DatoDummyn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.*;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-data", havingValue = "memory")
public class PersonajeServiceImpl implements IPersonajeService {

    @Autowired
    IPersonajeRepository personajeRepository;

    @Override
    public List<Personaje> obtenerTodosLosPersonajes() {

        return personajeRepository.obtenerTodosLosPersonajes();
    }

    @Override
    public List<Personaje> buscarPorEdadONombre(String dato) {
        if (dato == null || dato.isBlank()) {
            throw new IllegalArgumentException("El dato ingresado no puede ser nulo o estar vacio");
        }
        if (!dato.matches("^[a-zA-Z ]+$")) {
            throw new IllegalArgumentException("El dato ingresado no es valido, solo letras para personajes o numeros para edades");
        }

        if (dato.matches("^[0-9]+$")) {
            Integer datoAInteger = Integer.parseInt(dato);
            return buscarPersonajesPorEdad(datoAInteger);
        } else {
            return buscarPersonajePorNombre(dato);
        }
    }

    @Override
    public List<Personaje> buscarPersonajePorNombre(String nombre) {
        Boolean sonSoloLetras = nombre.matches("^[a-zA-Z ]+$");

        if (!sonSoloLetras) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Optional<Personaje> oPersonaje = personajeRepository.buscarPersonajePorNombre(nombre);
        if (oPersonaje.isEmpty()) {
            throw new NullPointerException("El nombre no existe en la base de datos");
        }
        Personaje personaje = oPersonaje.get();
        List<Personaje> listaPersonaje = new ArrayList<>();
        listaPersonaje.add(personaje);
        return listaPersonaje;
    }

    @Override
    public List<Personaje> buscarPersonajesPorEdad(Integer edad) {
        Optional<Personaje> oPersonaje= personajeRepository.buscarPersonajesPorEdad(edad);

        if (oPersonaje.isEmpty()) {
            throw new NullPointerException("La edad ingresada no corresponde con ningun personaje");
        }
        Personaje personaje = oPersonaje.get();
        List<Personaje> listaPersonaje = new ArrayList<>();
        listaPersonaje.add(personaje);
        return listaPersonaje;
    }

    @Override
    public List<Personaje> buscarPersonajePorRangoDeEdad(Integer desde, Integer hasta) {
        if (desde == null || hasta == null){
            throw new IllegalArgumentException("La edad no pueden ser nulas");
        }
        if (desde < 0 || hasta < 0) {
            throw new IllegalArgumentException("Las edades debe ser mayores a 0");
        }
        if (!(desde<=hasta)){
            throw new IllegalArgumentException("Las edades desde y hasta deben ser iguales o en orden ascendente");
        }

        List<Personaje> personajes = personajeRepository.buscarPersonajesPorRangoDeEdad(desde, hasta);

        if (personajes.isEmpty()) {
            throw new NullPointerException("No se encontro personajes con el rango indicado de edad");
        }

        return personajes;
    }

    @Override
    public Personaje agregarNuevoPersonaje(Personaje personaje) {
        Optional<Personaje> optionalPersonaje = personajeRepository.obtenerTodosLosPersonajes().stream().filter(p -> p.getNombre().equalsIgnoreCase(personaje.getNombre())).findAny();

        if (optionalPersonaje.isPresent()) {
            throw new IllegalArgumentException("El nombre del personaje ingresado ya existe");
        }
        if (personaje.getNombre().isBlank() || personaje.getNombre() == null) {
            throw new IllegalArgumentException("El nombre del personaje no puede estar vacio o ser nulo");
        }
        if (personaje.getEdad()>=0 || personaje.getEdad() == null){
            throw new IllegalArgumentException("La edad no puede ser negativa o nula");
        }
        if (personaje.getPeso()>0 || personaje.getPeso() == null) {
            throw new IllegalArgumentException("El peso no puede ser negativo o nulo");
        }
        if (!personaje.getHistoria().isBlank() || personaje.getHistoria() == null) {
            throw new IllegalArgumentException("La hisotira del personaje no puede estar vacia o ser nula");
        }

        Long cantidadDePersonajes = Long.valueOf(personajeRepository.obtenerTodosLosPersonajes().size());
        personaje.setId(cantidadDePersonajes + 10001);
        personajeRepository.agregarNuevoPersonaje(personaje);


        return personaje;
    }

    @Override
    public Personaje actualizarPersonajePorId(Long id, Personaje personajeAct) {
        Optional<Personaje> optionalPersonaje = personajeRepository.buscarPersonajePorId(id);

        if (optionalPersonaje.isPresent()) {
            Personaje personaje = optionalPersonaje.get();

            if (personajeAct.getNombre().isBlank()) {
                throw new IllegalArgumentException("El nombre no puede estar vacio o nulo");
            }
            if (personajeAct.getEdad() == null){
                throw new IllegalArgumentException("La edad no puede ser nula");
            }
            if (personajeAct.getPeso() == null) {
                throw new IllegalArgumentException("El peso no puede ser nulo");
            }
            if (personajeAct.getHistoria().isBlank()) {
                throw new IllegalArgumentException("La historia no puede ser nula o estar vacia");
            }

            personaje.setNombre(personajeAct.getNombre());
            personaje.setEdad(personajeAct.getEdad());
            personaje.setPeso(personajeAct.getPeso());
            personaje.setHistoria(personajeAct.getHistoria());

            if (personajeAct.getPeliculasSeries().size()>0){
                personaje.setPeliculasSeries(personajeAct.getPeliculasSeries());
            }

            return personaje;
        }else{
            throw new IllegalArgumentException("El id ingresado no existe");
        }
    }
}
