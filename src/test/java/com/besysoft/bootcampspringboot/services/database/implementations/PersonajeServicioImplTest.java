package com.besysoft.bootcampspringboot.services.database.implementations;

import com.besysoft.bootcampspringboot.DTO.Mapper.IPersonajeMapper;
import com.besysoft.bootcampspringboot.DTO.Request.PersonajeRequestDto;
import com.besysoft.bootcampspringboot.DTO.Response.PersonajeResponseDto;
import com.besysoft.bootcampspringboot.dominio.Personaje;
import com.besysoft.bootcampspringboot.respositories.database.Interfaces.IPersonajeRepository;
import com.besysoft.bootcampspringboot.services.interfaces.IPersonajeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.besysoft.bootcampspringboot.Util.DatosDummynPersonaje.*;
import static org.assertj.core.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PersonajeServicioImplTest {

    @MockBean
    private IPersonajeRepository personajeRepository;

    @Autowired
    private IPersonajeService personajeService;

    @Autowired
    private IPersonajeMapper mapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void obtenerTodosLosPersonajes() {

        List<PersonajeResponseDto> listaPersonajeDtoEsperada = LISTA_PERSONAJES
                .stream()
                .peek(System.out::println)
                .map(personaje -> mapper.mapToDto(personaje))
                .collect(Collectors.toList());

        when(personajeRepository.findAll()).thenReturn(LISTA_PERSONAJES);

        List<PersonajeResponseDto> listaPersonajeDtoActual = personajeService.obtenerTodosLosPersonajes();

        assertFalse(listaPersonajeDtoActual.isEmpty());
        assertEquals(listaPersonajeDtoEsperada.size(), listaPersonajeDtoActual.size());
        assertEquals(listaPersonajeDtoEsperada, listaPersonajeDtoActual);

        verify(personajeRepository).findAll();
    }

    @Test
    void buscarPorEdadONombreWithError() {

        String nombreExistente = "Harry Potter";
        String numeroNegativo = String.valueOf(-1);

        List<Personaje> listaPersonaje = LISTA_PERSONAJES
                .stream()
                .filter(personaje -> personaje
                        .getNombre().equalsIgnoreCase(nombreExistente))
                .collect(Collectors.toList());

        when(personajeRepository.findByNombreIgnoreCase(nombreExistente)).thenReturn(listaPersonaje);

        assertThatThrownBy(() -> personajeService.buscarPorEdadONombre(numeroNegativo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La edad no puede ser menor a 0");

    }

    @Test
    void buscarPersonajesPorEdad() {

        List<Personaje> personajesRepositorio = new ArrayList<>(Arrays.asList(getPersonajeConId3()));
        Integer edadExist = 10;

        List<PersonajeResponseDto> dtoEsperado = LISTA_PERSONAJES
                .stream()
                .filter(personaje -> personaje.getEdad().equals(edadExist))
                .peek(System.out::println)
                .map(personaje -> mapper.mapToDto(personaje))
                .collect(Collectors.toList());

        when(personajeRepository.findByEdad(anyInt())).thenReturn(personajesRepositorio);

        //WHEN
        List<PersonajeResponseDto> dtoActual = personajeService.buscarPersonajesPorEdad(edadExist);

        assertFalse(dtoActual.isEmpty());
        assertTrue(dtoActual.size() == 1);
        assertEquals(dtoEsperado, dtoActual);
        verify(personajeRepository).findByEdad(anyInt());
    }

    @Test
    void buscarPersonajePorNombre() {

        List<Personaje> personajesRepositorio = new ArrayList<>(Arrays.asList(getPersonajeConId3()));
        String nombreExist = "Harry Potter";

        List<PersonajeResponseDto> dtoEsperado = LISTA_PERSONAJES
                .stream()
                .filter(personaje -> personaje.getNombre().equals(nombreExist))
                .peek(System.out::println)
                .map(personaje -> mapper.mapToDto(personaje))
                .collect(Collectors.toList());

        when(personajeRepository.findByNombreIgnoreCase(anyString())).thenReturn(personajesRepositorio);

        //WHEN
        List<PersonajeResponseDto> dtoActual = personajeService.buscarPersonajePorNombre(nombreExist);

        assertFalse(dtoActual.isEmpty());
        assertTrue(dtoActual.size() == 1);
        assertEquals(dtoEsperado, dtoActual);
        verify(personajeRepository).findByNombreIgnoreCase(anyString());

    }

    @Test
    void buscarPersonajePorRangoDeEdad() {
        List<PersonajeResponseDto> dtoEsperado = LISTA_PERSONAJES
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());

        Integer desde = 10;
        Integer hasta = 28;

        when(personajeRepository.findByEdadBetween(anyInt(), anyInt()))
                .thenReturn(LISTA_PERSONAJES);

        //WHEN
        List<PersonajeResponseDto> dtoActual = personajeService.buscarPersonajePorRangoDeEdad(desde, hasta);


        //THEN
        assertEquals(dtoEsperado, dtoActual);
        verify(personajeRepository).findByEdadBetween(anyInt(), anyInt());
    }

    @Test
    void agregarNuevoPersonaje() {

        Personaje personaje = getPersonajeConId2();

        PersonajeRequestDto requestDto = new PersonajeRequestDto(
                personaje.getNombre(),
                personaje.getEdad(),
                personaje.getPeso(),
                personaje.getHistoria(),
                new ArrayList<>());

        PersonajeResponseDto dtoEsperado = mapper.mapToDto(personaje);

        when(personajeRepository.save(any(Personaje.class))).thenReturn(personaje);

        //WHEN
        PersonajeResponseDto dtoActual = personajeService.agregarNuevoPersonaje(requestDto);

        //THEN
        assertEquals(dtoEsperado, dtoActual);
        verify(personajeRepository).save(any(Personaje.class));
    }

    @Test
    void actualizarPersonajePorId() {

        Personaje personaje = getPersonajeConId1();
        Long id = personaje.getId();

        PersonajeRequestDto requestDto = new PersonajeRequestDto(
                personaje.getNombre(),
                personaje.getEdad(),
                personaje.getPeso(),
                personaje.getHistoria(),
                new ArrayList<>());

        PersonajeResponseDto dtoEsperado = mapper.mapToDto(personaje);

        when(personajeRepository.save(any(Personaje.class))).thenReturn(personaje);
        when(personajeRepository.findById(anyLong())).thenReturn(Optional.of(personaje));

        //WHEN
        PersonajeResponseDto dtoActual = personajeService.actualizarPersonajePorId(id, requestDto);

        //THEN
        assertEquals(dtoEsperado, dtoActual);
        verify(personajeRepository).save(any(Personaje.class));
        verify(personajeRepository).findById(anyLong());
    }
}