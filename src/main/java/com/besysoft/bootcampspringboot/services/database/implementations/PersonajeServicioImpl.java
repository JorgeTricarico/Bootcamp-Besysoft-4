package com.besysoft.bootcampspringboot.services.database.implementations;

import com.besysoft.bootcampspringboot.dominio.Personaje;

import com.besysoft.bootcampspringboot.respositories.database.Interfaces.IPersonajeRepository;
import com.besysoft.bootcampspringboot.services.interfaces.IPersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.*;
import static com.besysoft.bootcampspringboot.utilidades.Validaciones.validarLetras;

@ConditionalOnProperty(prefix = "app", name = "type-data", havingValue = "database")
@Service
public class PersonajeServicioImpl  implements IPersonajeService {

    @Autowired
    IPersonajeRepository personajeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Personaje> obtenerTodosLosPersonajes() {
            List<Personaje> personajes = personajeRepository.findAll();
            if (personajes.isEmpty()){
                throw  new NullPointerException("No hay personajes en la base de datos.");
            }
            return personajes;


    }

    @Override
    @Transactional(readOnly = true)
    public List<Personaje> buscarPorEdadONombre(String dato) {

        if (dato.matches("^[0-9]+$")) {
            Integer datoAInteger = Integer.parseInt(dato);
            if (datoAInteger<0){
                throw new IllegalArgumentException("La edad no puede ser menor a 0.");
            }
            return buscarPersonajesPorEdad(datoAInteger);
        } else {
            return buscarPersonajePorNombre(dato);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Personaje> buscarPersonajePorNombre(String nombre) {

        validarLetras("nombre de personaje", nombre);

        List<Personaje> personajes = personajeRepository.findByNombreIgnoreCase(nombre);
        if (personajes.isEmpty()) {
            throw new NullPointerException("El nombre '"+nombre+"' no existe en la base de datos");
        }

        return personajes;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Personaje> buscarPersonajesPorEdad(Integer edad) {
        List<Personaje> personajes= personajeRepository.findByEdad(edad);

        if (personajes.isEmpty()) {
            throw new NullPointerException("La edad '"+edad+"' no corresponde con ningun personaje");
        }
        return personajes;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Personaje> buscarPersonajePorRangoDeEdad(Integer desde, Integer hasta) {
        List<Personaje> personajes = personajeRepository.findByEdadBetween(desde, hasta);

        if (personajes.isEmpty()) {
            throw new NullPointerException("No se encontraron personajes en el rango de las edades ingresadas");
        }
        return personajes;
    }

    @Override
    @Transactional(readOnly = false)
    public Personaje agregarNuevoPersonaje(Personaje personaje) {
        Optional<Personaje> optionalPersonaje = personajeRepository.findAll().stream().filter(p -> p.getNombre().equalsIgnoreCase(personaje.getNombre())).findAny();

        if (optionalPersonaje.isPresent()) {
            throw new IllegalArgumentException("El nombre ingresado del personaje '"+ personaje.getNombre()+"' ya existe");
        }
        personajeRepository.save(personaje);

        return personaje;
    }

    @Override
    @Transactional(readOnly = false)
    public Personaje actualizarPersonajePorId(Long id, Personaje personajeAct) {
        Optional<Personaje> optionalPersonaje = personajeRepository.findById(id);

        if (optionalPersonaje.isPresent()) {
            Personaje personaje = optionalPersonaje.get();
            /*Long cantidadDePersonajes = personajeRepository.count();
            System.out.println(cantidadDePersonajes);
            if (personajeAct.getId()>cantidadDePersonajes){
                throw new IllegalArgumentException("El id no puede se mayor a la cantidad de personajes que actualmente es: " + cantidadDePersonajes);
            }
            personaje.setId(personajeAct.getId());*/

            personaje.setNombre(personajeAct.getNombre());
            personaje.setEdad(personajeAct.getEdad());
            personaje.setPeso(personajeAct.getPeso());
            personaje.setHistoria(personajeAct.getHistoria());

            if (personajeAct.getPeliculasSeries() != null){
                personaje.setPeliculasSeries(personajeAct.getPeliculasSeries());
            }

            return personaje;
        }else{
            throw new NullPointerException("El id de personaje ingresado numero '"+id+"'  no existe en la base de datos");
        }
    }
}
