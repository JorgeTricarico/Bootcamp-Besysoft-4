package com.besysoft.bootcampspringboot.services.database.implementations;

import com.besysoft.bootcampspringboot.DTO.Mapper.IGeneroMapper;
import com.besysoft.bootcampspringboot.DTO.Response.GeneroResponseDto;

import com.besysoft.bootcampspringboot.respositories.database.Interfaces.IGeneroRepository;
import com.besysoft.bootcampspringboot.services.interfaces.IGeneroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.stream.Collectors;

import static com.besysoft.bootcampspringboot.Util.DatosDummynGenero.LISTA_GENERO_SIN_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class GeneroServiceImplTest {

    @MockBean
    private IGeneroRepository generoRespository;

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

        when(generoRespository.findAll()).thenReturn(LISTA_GENERO_SIN_ID);

        List<GeneroResponseDto> listaGeneroDtoActual = generoService.obtenerTodosLosGeneros();

        assertFalse(listaGeneroDtoActual.isEmpty());
        assertEquals(listaGeneroDtoEsperada.size(), listaGeneroDtoActual.size());
        assertEquals(listaGeneroDtoEsperada, listaGeneroDtoActual);

        verify(generoRespository).findAll();
    }

    @Test
    void agregarNuevoGenero() {
    }

    @Test
    void actualizarGeneroPorId() {
    }
}