package com.besysoft.bootcampspringboot.services.database.implementations;

import com.besysoft.bootcampspringboot.DTO.Mapper.IGeneroMapper;
import com.besysoft.bootcampspringboot.DTO.Mapper.IPeliculaSerieMapper;
import com.besysoft.bootcampspringboot.DTO.Request.GeneroRequestDto;
import com.besysoft.bootcampspringboot.DTO.Request.PeliculaSerieRequestDto;
import com.besysoft.bootcampspringboot.DTO.Response.PeliculaSerieResponseDto;
import com.besysoft.bootcampspringboot.Util.DatosDummynPelicula;
import com.besysoft.bootcampspringboot.dominio.Genero;
import com.besysoft.bootcampspringboot.dominio.PeliculaSerie;
import com.besysoft.bootcampspringboot.respositories.database.Interfaces.IPeliculaSerieRepository;
import com.besysoft.bootcampspringboot.services.interfaces.IPeliculaSerieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.besysoft.bootcampspringboot.Util.DatosDummynGenero.getGeneroSinId1;
import static com.besysoft.bootcampspringboot.Util.DatosDummynPelicula.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
@SpringBootTest
class PeliculaServicioImplTest {

    @MockBean
    private IPeliculaSerieRepository peliculaRepository;

    @Autowired
    private IPeliculaSerieService peliculaService;

    @Autowired
    private IPeliculaSerieMapper mapper;
    @Autowired
    private IGeneroMapper mapperGenero;

    @BeforeEach
    void setUp() {
    }


    @Test
    void obtenerTodasLasPeliculas() {
        List<PeliculaSerieResponseDto> listaPeliculaDtoEsperada = LISTA_PELICULAS
                .stream()
                .peek(System.out::println)
                .map(pelicula -> mapper.mapToDto(pelicula))
                .collect(Collectors.toList());

        when(peliculaRepository.findAll()).thenReturn(LISTA_PELICULAS);

        List<PeliculaSerieResponseDto> listaPeliculaDtoActual = peliculaService.obtenerTodasLasPeliculas();

        assertFalse(listaPeliculaDtoActual.isEmpty());
        assertEquals(listaPeliculaDtoEsperada.size(), listaPeliculaDtoActual.size());
        assertEquals(listaPeliculaDtoEsperada, listaPeliculaDtoActual);

        verify(peliculaRepository).findAll();
    }

    @Test
    void buscarPeliculaPorTitulo() {

        PeliculaSerie peliculaRepo = getPeliculaConId1();
        List<PeliculaSerie> peliculasRepositorio = new ArrayList<>(Arrays.asList(peliculaRepo));

        String tituloExist = getPeliculaConId1().getTitulo();

        List<PeliculaSerieResponseDto> dtoEsperado = LISTA_PELICULAS
                .stream()
                .filter(pelicula -> pelicula.getTitulo().equals(tituloExist))
                .peek(System.out::println)
                .map(pelicula -> mapper.mapToDto(pelicula))
                .collect(Collectors.toList());

        when(peliculaRepository.findAll()).thenReturn(Collections.emptyList());
        when(peliculaRepository.findByTituloIgnoreCase(anyString())).thenReturn(Optional.of(peliculaRepo));

        //WHEN
        List<PeliculaSerieResponseDto> dtoActual = peliculaService.buscarPeliculaPorTituloOGenero(tituloExist);

        assertFalse(dtoActual.isEmpty());
        assertTrue(dtoActual.size() == 1);
        assertEquals(dtoEsperado, dtoActual);
        verify(peliculaRepository).findByTituloIgnoreCase(anyString());
    }

    @Test
    void buscarPeliculaPorGenero() {

        Genero genero = getGeneroSinId1();
        List<PeliculaSerie> peliculasRepositorio = LISTA_PELICULAS;

        String generoExistInPeliculas = genero.getNombre();

        List<PeliculaSerieResponseDto> dtoEsperado = LISTA_PELICULAS
                .stream()
                .filter(pelicula -> pelicula.getGenero().getNombre().equalsIgnoreCase(generoExistInPeliculas))
                .peek(System.out::println)
                .map(pelicula -> mapper.mapToDto(pelicula))
                .collect(Collectors.toList());

        when(peliculaRepository.findAll()).thenReturn(LISTA_PELICULAS);

        //WHEN
        List<PeliculaSerieResponseDto> dtoActual = peliculaService.buscarPeliculaPorTituloOGenero(generoExistInPeliculas);

        assertFalse(dtoActual.isEmpty());
        assertTrue(dtoActual.size() == 3);
        assertEquals(dtoEsperado, dtoActual);
        verify(peliculaRepository).findAll();

    }

    @Test
    void buscarPeliculaPorFecha() {

        String desde = "15-01-1998";
        String hasta = "02-02-2001";

        List<PeliculaSerieResponseDto> dtoEsperado = LISTA_PELICULAS
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());

        when(peliculaRepository.findAllByFechaDeCreacionBetween(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(LISTA_PELICULAS);

        //WHEN
        List<PeliculaSerieResponseDto> dtoActual = peliculaService.buscarPeliculaPorFecha(desde, hasta);

        //THEN
        assertEquals(dtoEsperado, dtoActual);
        verify(peliculaRepository).findAllByFechaDeCreacionBetween(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    void buscarPeliculasPorCalificacion() {

        List<PeliculaSerie> listaPeliculas = new ArrayList<>(Arrays.asList(getPeliculaConId2(),getPeliculaConId2()));

        List<PeliculaSerieResponseDto> dtoEsperado = listaPeliculas
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());

        Integer desde = 4;
        Integer hasta = 5;

        when(peliculaRepository.findAllByCalificacionBetween(anyInt(), anyInt())).thenReturn(listaPeliculas);

        //WHEN
        List<PeliculaSerieResponseDto> dtoActual = peliculaService.buscarPeliculasPorCalificacion(desde, hasta);

        //THEN
        assertEquals(dtoEsperado, dtoActual);
        verify(peliculaRepository).findAllByCalificacionBetween(anyInt(), anyInt());
    }

    @Test
    void agregarNuevaPelicula() {
        PeliculaSerie pelicula = getPeliculaConId2();

        PeliculaSerieRequestDto requestDto = new PeliculaSerieRequestDto(
                pelicula.getTitulo(),
                pelicula.getFechaDeCreacion().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                pelicula.getCalificacion(),
                new GeneroRequestDto(pelicula.getGenero().getNombre()));

        PeliculaSerieResponseDto dtoEsperado = mapper.mapToDto(pelicula);

        when(peliculaRepository.save(any(PeliculaSerie.class))).thenReturn(pelicula);

        //WHEN
        PeliculaSerieResponseDto dtoActual = peliculaService.agregarNuevaPelicula(requestDto);

        //THEN
        assertEquals(dtoEsperado.getTitulo(), dtoActual.getTitulo());
        verify(peliculaRepository).save(any(PeliculaSerie.class));
    }

    @Test
    void actualizarPeliculaPorId() {
        PeliculaSerie pelicula = DatosDummynPelicula.getPeliculaConId2();
        Long id = pelicula.getId();

        PeliculaSerieRequestDto requestDto = new PeliculaSerieRequestDto(
                pelicula.getTitulo(),
                pelicula.getFechaDeCreacion().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                pelicula.getCalificacion(),
                new GeneroRequestDto(pelicula.getGenero().getNombre()));

        PeliculaSerieResponseDto dtoEsperado = mapper.mapToDto(pelicula);

        when(peliculaRepository.save(any(PeliculaSerie.class))).thenReturn(pelicula);
        when(peliculaRepository.findById(anyLong())).thenReturn(Optional.of(pelicula));

        //WHEN
        PeliculaSerieResponseDto dtoActual = peliculaService.actualizarPeliculaPorId(id, requestDto);

        //THEN
        assertEquals(dtoEsperado, dtoActual);
        verify(peliculaRepository).save(any(PeliculaSerie.class));
        verify(peliculaRepository).findById(anyLong());
    }
}