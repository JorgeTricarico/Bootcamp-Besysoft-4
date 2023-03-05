package com.besysoft.bootcampspringboot.services.database.implementations;

import com.besysoft.bootcampspringboot.DTO.Mapper.IPersonajeMapper;
import com.besysoft.bootcampspringboot.DTO.Request.PersonajeRequestDto;
import com.besysoft.bootcampspringboot.DTO.Response.PersonajeResponseDto;
import com.besysoft.bootcampspringboot.dominio.Personaje;
import com.besysoft.bootcampspringboot.respositories.database.Interfaces.IPersonajeRepository;
import com.besysoft.bootcampspringboot.services.interfaces.IPersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.besysoft.bootcampspringboot.utilidades.Validaciones.validarLetras;

@ConditionalOnProperty(prefix = "app", name = "type-data", havingValue = "database")
@Service
public class PersonajeServicioImpl  implements IPersonajeService {

    @Autowired
    IPersonajeRepository personajeRepository;
    @Autowired
    IPersonajeMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<PersonajeResponseDto> obtenerTodosLosPersonajes() {
            List<Personaje> personajes = personajeRepository.findAll();
            if (personajes.isEmpty()){
                throw  new NullPointerException("No hay personajes en la base de datos.");
            }

            List<PersonajeResponseDto> personajeResponseDtos = personajes.stream()
                    .map(personaje -> mapper
                            .mapToDto(personaje))
                    .collect(Collectors.toList());

            return personajeResponseDtos;


    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonajeResponseDto> buscarPorEdadONombre(String edadONombre) {

        if (edadONombre.matches("^[-+]?\\d+$")) {  //^[-+]?\\d+$    ^[0-9]+$
            Integer datoAInteger = Integer.parseInt(edadONombre);
            if (datoAInteger<0){
                throw new IllegalArgumentException("La edad no puede ser menor a 0");
            }
            return buscarPersonajesPorEdad(datoAInteger);
        } else {
            return buscarPersonajePorNombre(edadONombre);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonajeResponseDto> buscarPersonajePorNombre(String nombre) {

        validarLetras("nombre de personaje", nombre);

        List<Personaje> personajes = personajeRepository.findByNombreIgnoreCase(nombre);
        if (personajes.isEmpty()) {
            throw new NullPointerException("El nombre '"+nombre+"' no existe en la base de datos");
        }

        List<PersonajeResponseDto> personajeResponseDtos = personajes.stream()
                .map(personaje -> mapper
                        .mapToDto(personaje))
                .collect(Collectors.toList());

        return personajeResponseDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonajeResponseDto> buscarPersonajesPorEdad(Integer edad) {
        List<Personaje> personajes= personajeRepository.findByEdad(edad);

        if (personajes.isEmpty()) {
            throw new NullPointerException("La edad '"+edad+"' no corresponde con ningun personaje");
        }
        List<PersonajeResponseDto> personajeResponseDtos = personajes.stream()
                .map(personaje -> mapper
                        .mapToDto(personaje))
                .collect(Collectors.toList());
        return personajeResponseDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonajeResponseDto> buscarPersonajePorRangoDeEdad(Integer desde, Integer hasta) {
        List<Personaje> personajes = personajeRepository.findByEdadBetween(desde, hasta);

        if (personajes.isEmpty()) {
            throw new NullPointerException("No se encontraron personajes en el rango de las edades ingresadas");
        }
        List<PersonajeResponseDto> personajeResponseDtos = personajes.stream()
                .map(personaje -> mapper
                        .mapToDto(personaje))
                .collect(Collectors.toList());

        return personajeResponseDtos;
    }

    @Override
    @Transactional(readOnly = false)
    public PersonajeResponseDto agregarNuevoPersonaje(PersonajeRequestDto personajeRequestDto) {
        Optional<Personaje> optionalPersonaje = personajeRepository.findAll().stream().filter(p -> p.getNombre().equalsIgnoreCase(personajeRequestDto.getNombre())).findAny();

        if (optionalPersonaje.isPresent()) {
            throw new IllegalArgumentException("El nombre ingresado del personaje '"+ personajeRequestDto.getNombre()+"' ya existe");
        }
        Personaje personaje = mapper.mapToEntity(personajeRequestDto);
        personajeRepository.save(personaje);

        PersonajeResponseDto personajeResponseDto = mapper.mapToDto(personaje);
        return personajeResponseDto;
    }

    @Override
    @Transactional(readOnly = false)
    public PersonajeResponseDto actualizarPersonajePorId(Long id, PersonajeRequestDto personajeRequestDto) {
        Optional<Personaje> optionalPersonaje = personajeRepository.findById(id);

        if (optionalPersonaje.isPresent()) {
            Personaje personaje = mapper.mapToEntity(personajeRequestDto);
            personaje.setId(id);
            personajeRepository.save(personaje);
            PersonajeResponseDto personajeResponseDto = mapper.mapToDto(personaje);
            return personajeResponseDto;
        }else{
            throw new NullPointerException("El id de personaje ingresado numero '"+id+"'  no existe en la base de datos");
        }
    }
}
