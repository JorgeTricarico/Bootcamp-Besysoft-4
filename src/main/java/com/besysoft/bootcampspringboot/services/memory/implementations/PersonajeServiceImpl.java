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

import java.util.List;
import java.util.Optional;

import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.*;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-data", havingValue = "memory")
public class PersonajeServiceImpl implements IPersonajeService {

    @Autowired
    IPersonajeRepository personajeRepository;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> obtenerTodosLosPersonajes() {

        return (ResponseEntity<?>) personajeRepository.obtenerTodosLosPersonajes();
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> buscarPorEdadONombre(String dato) {
        if (dato == null || dato.isBlank()) {
            return DatoDummyn.badResquest("El dato ingresado no puede ser nulo o estar vacio");
        }
        if (!dato.matches("^[a-zA-Z ]+$")) {
            badResquest("El dato ingresado no es valido, solo letras para personajes o numeros para edades");
        }

        if (dato.matches("^[0-9]+$")) {
            Integer datoAInteger = Integer.parseInt(dato);
            return buscarPersonajesPorEdad(datoAInteger);
        } else {
            return buscarPersonajePorNombre(dato);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> buscarPersonajePorNombre(String nombre) {
        Boolean sonSoloLetras = nombre.matches("^[a-zA-Z ]+$");

        if (!sonSoloLetras) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Optional<Personaje> oPersonaje = personajeRepository.buscarPersonajePorNombre(nombre);
        if (oPersonaje.isEmpty()) {
            return notFound("El nombre '%s' no existe en la base de datos", nombre);
        }
        Personaje personaje = oPersonaje.get();
        HttpHeaders headers = headers();
        return new ResponseEntity<>(personaje, headers, HttpStatus.CREATED);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> buscarPersonajesPorEdad(Integer edad) {
        Optional<Personaje> oPersonaje= personajeRepository.buscarPersonajesPorEdad(edad);

        if (oPersonaje.isEmpty()) {
            return notFound("La edad ingresada no corresponde con ningun personaje");
        }
        Personaje personaje = oPersonaje.get();

        return new ResponseEntity<>(personaje,headers(),HttpStatus.ACCEPTED);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> buscarPersonajePorRangoDeEdad(Integer desde, Integer hasta) {
        if (desde == null || hasta == null){
            return badResquest("La edad no pueden ser nulas");
        }
        if (desde < 0 || hasta < 0) {
            return badResquest("Las edades debe ser mayores a 0");
        }
        if (!(desde<=hasta)){
            return badResquest("Las edades desde y hasta deben ser iguales o en orden ascendente");
        }

        List<Personaje> personajes = personajeRepository.buscarPersonajesPorRangoDeEdad(desde, hasta);

        if (personajes.isEmpty()) {
            return notFound("No se encontro personajes con el rango indicado de edad");
        }

        return new ResponseEntity(personajes, headers(), HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = false)
    public ResponseEntity agregarNuevoPersonaje(Personaje personaje) {
        Optional<Personaje> optionalPersonaje = personajeRepository.obtenerTodosLosPersonajes().stream().filter(p -> p.getNombre().equalsIgnoreCase(personaje.getNombre())).findAny();

        if (optionalPersonaje.isPresent()) {
            return badResquest("El nombre del personaje '%s' ingresado ya existe", personaje.getNombre());
        }
        if (personaje.getNombre().isBlank() || personaje.getNombre() == null) {
            return badResquest("El nombre del personaje no puede estar vacio o ser nulo");
        }
        if (personaje.getEdad()>=0 || personaje.getEdad() == null){
            return badResquest("La edad no puede ser negativa o nula");
        }
        if (personaje.getPeso()>0 || personaje.getPeso() == null) {
            return badResquest("El peso no puede ser negativo o nulo");
        }
        if (!personaje.getHistoria().isBlank() || personaje.getHistoria() == null) {
            return badResquest("La hisotira del personaje no puede estar vacia o ser nula");
        }

        Long cantidadDePersonajes = Long.valueOf(personajeRepository.obtenerTodosLosPersonajes().size());
        personaje.setId(cantidadDePersonajes + 10001);
        personajeRepository.agregarNuevoPersonaje(personaje);


        return new ResponseEntity(personaje, headers(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity actualizarPersonajePorId(Long id, Personaje personajeAct) {
        Optional<Personaje> optionalPersonaje = personajeRepository.buscarPersonajePorId(id);

        if (optionalPersonaje.isPresent()) {
            Personaje personaje = optionalPersonaje.get();

            if (personajeAct.getNombre().isBlank()) {
                return badResquest("El nombre no puede estar vacio o nulo");
            }
            if (personajeAct.getEdad() == null){
                return badResquest("La edad no puede ser nula");
            }
            if (personajeAct.getPeso() == null) {
                return badResquest("El peso no puede ser nulo");
            }
            if (personajeAct.getHistoria().isBlank()) {
                return badResquest("La historia no puede ser nula o estar vacia");
            }

            personaje.setNombre(personajeAct.getNombre());
            personaje.setEdad(personajeAct.getEdad());
            personaje.setPeso(personajeAct.getPeso());
            personaje.setHistoria(personajeAct.getHistoria());

            if (personajeAct.getPeliculasSeries().size()>0){
                personaje.setPeliculasSeries(personajeAct.getPeliculasSeries());
            }

            return new ResponseEntity(personaje, headers(), HttpStatus.OK);
        }else{
            return badResquest("El id %s ingresado no existe", id);
        }
    }
}
