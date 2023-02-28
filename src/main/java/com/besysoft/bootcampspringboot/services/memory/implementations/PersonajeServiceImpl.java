package com.besysoft.bootcampspringboot.services.memory.implementations;

import com.besysoft.bootcampspringboot.DTO.Mapper.IPersonajeMapper;
import com.besysoft.bootcampspringboot.DTO.Request.PersonajeRequestDto;
import com.besysoft.bootcampspringboot.DTO.Response.PersonajeResponseDto;
import com.besysoft.bootcampspringboot.dominio.Personaje;
import com.besysoft.bootcampspringboot.respositories.memory.interfaces.IPersonajeRepository;
import com.besysoft.bootcampspringboot.services.interfaces.IPersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-data", havingValue = "memory")
public class PersonajeServiceImpl implements IPersonajeService {

    @Autowired
    IPersonajeRepository personajeRepository;
    @Autowired
    IPersonajeMapper mapper;
    @Override
    public List<PersonajeResponseDto> obtenerTodosLosPersonajes() {
        List<PersonajeResponseDto> personajeResponseDtos = personajeRepository.obtenerTodosLosPersonajes()
                .stream()
                .map(personaje -> mapper
                        .mapToDto(personaje))
                .collect(Collectors.toList());

        return personajeResponseDtos;
    }

    @Override
    public List<PersonajeResponseDto> buscarPorEdadONombre(String dato) {
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
    public List<PersonajeResponseDto> buscarPersonajePorNombre(String nombre) {
        Boolean sonSoloLetras = nombre.matches("^[a-zA-Z ]+$");

        if (!sonSoloLetras) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Optional<Personaje> oPersonaje = personajeRepository.buscarPersonajePorNombre(nombre);
        if (oPersonaje.isEmpty()) {
            throw new NullPointerException("El nombre no existe en la base de datos");
        }
        Personaje personaje = oPersonaje.get();
        PersonajeResponseDto personajeResponseDto = mapper.mapToDto(personaje);
        List<PersonajeResponseDto> listaPersonajeResponseDto = new ArrayList<>();
        listaPersonajeResponseDto.add(personajeResponseDto);

        return listaPersonajeResponseDto;
    }

    @Override
    public List<PersonajeResponseDto> buscarPersonajesPorEdad(Integer edad) {
        Optional<Personaje> oPersonaje= personajeRepository.buscarPersonajesPorEdad(edad);

        if (oPersonaje.isEmpty()) {
            throw new NullPointerException("La edad ingresada no corresponde con ningun personaje");
        }
        Personaje personaje = oPersonaje.get();
        PersonajeResponseDto personajeResponseDto = mapper.mapToDto(personaje);
        List<PersonajeResponseDto> listaPersonajeDTO = new ArrayList<>();
        listaPersonajeDTO.add(personajeResponseDto);

        return listaPersonajeDTO;
    }

    @Override
    public List<PersonajeResponseDto> buscarPersonajePorRangoDeEdad(Integer desde, Integer hasta) {
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

        List<PersonajeResponseDto> listaPersonajeResponseDto = personajes.stream().map(personaje -> mapper.mapToDto(personaje)).collect(Collectors.toList());
        return listaPersonajeResponseDto;
    }

    @Override
    public PersonajeResponseDto agregarNuevoPersonaje(PersonajeRequestDto personajeRequestDto) {
        Optional<Personaje> optionalPersonaje = personajeRepository.obtenerTodosLosPersonajes().stream().filter(p -> p.getNombre().equalsIgnoreCase(personajeRequestDto.getNombre())).findAny();

        if (optionalPersonaje.isPresent()) {
            throw new NullPointerException("El nombre del personaje ingresado ya existe");
        }

        Personaje personaje = mapper.mapToEntity(personajeRequestDto);
        Long cantidadDePersonajes = Long.valueOf(personajeRepository.obtenerTodosLosPersonajes().size());
        personaje.setId(cantidadDePersonajes + 1);
        personajeRepository.agregarNuevoPersonaje(personaje);

        PersonajeResponseDto personajeResponseDto = mapper.mapToDto(personaje);

        return personajeResponseDto;
    }

    @Override
    public PersonajeResponseDto actualizarPersonajePorId(Long id, PersonajeRequestDto personajeRequestDto) {
        Optional<Personaje> optionalPersonaje = personajeRepository.buscarPersonajePorId(id);

        if (optionalPersonaje.isPresent()) {
            Personaje personaje = optionalPersonaje.get();


            personaje.setNombre(personajeRequestDto.getNombre());
            personaje.setEdad(personajeRequestDto.getEdad());
            personaje.setPeso(personajeRequestDto.getPeso());
            personaje.setHistoria(personajeRequestDto.getHistoria());

            if (personajeRequestDto.getPeliculasSeries().size()>0){
                personaje.setPeliculasSeries(mapper.mapToEntity(personajeRequestDto).getPeliculasSeries());
            }

            PersonajeResponseDto personajeResponseDto = mapper.mapToDto(personaje);
            return personajeResponseDto;
        }else{
            throw new IllegalArgumentException("El id ingresado no existe");
        }
    }
}
