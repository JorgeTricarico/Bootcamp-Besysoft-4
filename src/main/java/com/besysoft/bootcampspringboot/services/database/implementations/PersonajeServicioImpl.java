package com.besysoft.bootcampspringboot.services.database.implementations;

import com.besysoft.bootcampspringboot.dominio.Personaje;

import com.besysoft.bootcampspringboot.respositories.database.Interfaces.IPersonajeRepository;
import com.besysoft.bootcampspringboot.services.interfaces.IPersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.*;

@ConditionalOnProperty(prefix = "app", name = "type-data", havingValue = "database")
@Service
public class PersonajeServicioImpl  implements IPersonajeService {

    @Autowired
    IPersonajeRepository personajeRepository;

    @Override
    public ResponseEntity<?> obtenerTodosLosPersonajes() {
        List<Personaje> personajes = personajeRepository.findAll();
        return new ResponseEntity(personajes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> buscarPorEdadONombre(String dato) {

        if (dato.isBlank()) {
            return badResquest("El dato ingresado no puede ser nulo o estar vacio");
        }
        if (!dato.matches("^[a-zA-Z0-9 ]+$")) {
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
    public ResponseEntity<?> buscarPersonajePorNombre(String nombre) {

        Boolean sonSoloLetras = nombre.matches("^[a-zA-Z ]+$");

        if (!sonSoloLetras) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<Personaje> personajes = personajeRepository.findByNombreIgnoreCase(nombre);
        if (personajes.isEmpty()) {
            return notFound("El nombre '%s' no existe en la base de datos",nombre );
        }

        HttpHeaders headers = headers();
        return new ResponseEntity<>(personajes, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> buscarPersonajesPorEdad(Integer edad) {
        List<Personaje> personajes= personajeRepository.findByEdad(edad);

        if (personajes.isEmpty()) {
            return notFound("La edad '%d'ingresada no corresponde con ningun personaje", edad);
        }


        return new ResponseEntity<>(personajes, HttpStatus.ACCEPTED);
    }

    @Override
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

        List<Personaje> personajes = personajeRepository.findByEdadBetween(desde, hasta);

        if (personajes.isEmpty()) {
            return notFound("No se encontro personajes con el rango indicado de edad");
        }

        return new ResponseEntity(personajes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity agregarNuevoPersonaje(Personaje personaje) {

        if (personaje.getNombre().isBlank() || personaje.getNombre() == null) {
            return badResquest("El nombre del personaje no puede estar vacio o ser nulo");
        }

        Optional<Personaje> optionalPersonaje = personajeRepository.findAll().stream().filter(p -> p.getNombre().equalsIgnoreCase(personaje.getNombre())).findAny();

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
        if (!personaje.getHistoria().isBlank()) {
            return badResquest("La hisotira del personaje no puede estar vacia o ser nula");
        }

        //Long cantidadDePersonajes = Long.valueOf(personajeRepository.findAll().size());
        //personaje.setId(cantidadDePersonajes + 10001);
        personajeRepository.save(personaje);


        return new ResponseEntity(personaje, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity actualizarPersonajePorId(Long id, Personaje personajeAct) {
        Optional<Personaje> optionalPersonaje = personajeRepository.findById(id);

        if (optionalPersonaje.isPresent()) {
            Personaje personaje = optionalPersonaje.get();

            if (personajeAct.getNombre().isBlank()) {
                return badResquest("El nombre no puede estar vacio o nulo");
            }
            if (personaje.getEdad()>=0 || personajeAct.getEdad() == null){
                return badResquest("La edad tiene que ser un numero y no puede ser nula");
            }
            if (personaje.getEdad()>=0 || personajeAct.getPeso() == null) {
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

            return new ResponseEntity(personaje, HttpStatus.OK);
        }else{
            return notFound("El id %s ingresado no existe", id);
        }
    }
}
