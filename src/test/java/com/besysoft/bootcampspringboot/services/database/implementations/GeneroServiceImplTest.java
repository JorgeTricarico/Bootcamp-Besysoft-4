package com.besysoft.bootcampspringboot.services.database.implementations;

import com.besysoft.bootcampspringboot.DTO.Mapper.IGeneroMapper;
import com.besysoft.bootcampspringboot.DTO.Request.GeneroRequestDto;
import com.besysoft.bootcampspringboot.DTO.Response.GeneroResponseDto;
import com.besysoft.bootcampspringboot.Util.DatosDummynGenero;
import com.besysoft.bootcampspringboot.dominio.Genero;
import com.besysoft.bootcampspringboot.respositories.database.Interfaces.IGeneroRepository;
import com.besysoft.bootcampspringboot.services.interfaces.IGeneroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.besysoft.bootcampspringboot.Util.DatosDummynGenero.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class GeneroServiceImplTest {

    @MockBean
    private IGeneroRepository generoRepository;

    @Autowired
    private IGeneroService generoService;

    @Autowired
    private IGeneroMapper mapper;

    @BeforeEach
    void setUp() {
        //GIVEN GLOBAL
    }

    @Test
    void obtenerTodosLosGeneros() {

        List<GeneroResponseDto> listaGeneroDtoEsperada = LISTA_GENERO_SIN_ID
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());

        when(generoRepository.findAll()).thenReturn(LISTA_GENERO_SIN_ID);

        List<GeneroResponseDto> listaGeneroDtoActual = generoService.obtenerTodosLosGeneros();

        assertFalse(listaGeneroDtoActual.isEmpty());
        assertEquals(listaGeneroDtoEsperada.size(), listaGeneroDtoActual.size());
        assertEquals(listaGeneroDtoEsperada, listaGeneroDtoActual);

        verify(generoRepository).findAll();
    }

    @Test
    void agregarNuevoGenero() {

        GeneroRequestDto generoResquestDto = new GeneroRequestDto(getGeneroSinId1().getNombre());

        Genero genero = DatosDummynGenero.getGeneroSinId1();

        GeneroResponseDto generoResponseDtoEsperado = mapper.mapToDto(genero);


        when(generoRepository.save(any(Genero.class))).thenReturn(genero);

        //WHEN
        GeneroResponseDto generoResponseDtoActual = generoService.agregarNuevoGenero(generoResquestDto);

        //THEN
        assertEquals(generoResponseDtoEsperado, generoResponseDtoActual);

        verify(generoRepository).save(any(Genero.class));

    }

    @Test
    void actualizarGeneroPorId() {

        Genero genero = getGeneroConId(); //Genero en Base

        Long id = genero.getId();

        GeneroRequestDto generoRequestDto = new GeneroRequestDto(getGeneroSinId1().getNombre());

        GeneroResponseDto generoResponseDtoEsperado = new GeneroResponseDto();
        generoResponseDtoEsperado.setNombre(getGeneroSinId1().getNombre());

        //WHEN
        when(generoRepository.existsGeneroByNombreIgnoreCase(anyString())).thenReturn(false);
        when(generoRepository.findById(id)).thenReturn(Optional.of(genero));
        when(generoRepository.existsGeneroById(anyLong())).thenReturn(true);

        GeneroResponseDto generoResponseDtoActual = generoService.actualizarGeneroPorId(id, generoRequestDto);

        //THEN

        assertEquals(generoResponseDtoEsperado, generoResponseDtoActual);
        assertEquals(generoRequestDto.getNombre(), generoResponseDtoActual.getNombre());
        verify(generoRepository).findById(anyLong());
    }
}