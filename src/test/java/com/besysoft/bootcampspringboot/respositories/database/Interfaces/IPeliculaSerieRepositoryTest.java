package com.besysoft.bootcampspringboot.respositories.database.Interfaces;

import com.besysoft.bootcampspringboot.dominio.Genero;
import com.besysoft.bootcampspringboot.dominio.PeliculaSerie;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.besysoft.bootcampspringboot.Util.DatosDummynGenero.getGeneroSinId1;
import static com.besysoft.bootcampspringboot.Util.DatosDummynPelicula.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IPeliculaSerieRepositoryTest {

    private final PeliculaSerie pelicula1 = getPeliculaConId1();
    private final PeliculaSerie pelicula2 = getPeliculaConId2();
    private final PeliculaSerie pelicula3 = getPeliculaConId3();


    @Autowired
    IPeliculaSerieRepository peliculaRepository;
    @Autowired
    IGeneroRepository generoRepository;

    //List<PeliculaSerie> listaPeliculas = DatoDummyn.listaDePeliculas;
    @BeforeEach
    void setUp() {

        //GIVEN GLOBAL
        Genero genero1 = generoRepository.save(getGeneroSinId1());
        pelicula1.setGenero(genero1);
        pelicula2.setGenero(genero1);
        pelicula3.setGenero(genero1);
        peliculaRepository.save(pelicula1);
        peliculaRepository.save(pelicula2);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Order(1)
    void save() {
        //GIVEN

        //WHEN
        PeliculaSerie peliculaSave = peliculaRepository.save(pelicula3);

        //THEN
        assertEquals(pelicula3.getTitulo(), peliculaSave.getTitulo());

    }

    @Test
    @Order(2)
    void findAll() {
        //WHEN
        List<PeliculaSerie> peliculas = peliculaRepository.findAll();

        //THEN
        assertFalse(peliculas.isEmpty());
        assertEquals(23, peliculas.size());
    }

    @Test
    @Order(3)
    void findByTituloIgnoreCase() {
        //Given
        String tituloPeliculaExist = pelicula1.getTitulo(); //"Ansestros Perdidos";
        String tituloPeliculaUnexist = "Pelicula Inexistente";

        //WHEN
        Optional<PeliculaSerie> oPeliculaExit = peliculaRepository.findByTituloIgnoreCase(tituloPeliculaExist);
        Optional<PeliculaSerie> oPeliculaUnexit = peliculaRepository.findByTituloIgnoreCase(tituloPeliculaUnexist);

        //THEN
        assertTrue(oPeliculaExit.isPresent());
        assertEquals(oPeliculaExit.get().getTitulo(), tituloPeliculaExist);
        assertTrue(oPeliculaUnexit.isEmpty());
    }

    @Test
    @Order(4)
    void findAllByFechaDeCreacionBetween() {
        //GIVEN
        LocalDate desde = LocalDate.parse("1998-01-01");
        LocalDate hasta = LocalDate.parse("2000-01-01");

        //WHEN
        List<PeliculaSerie> peliculaPorStream= peliculaRepository.findAll()
                .stream()
                .filter(peli -> peli.getFechaDeCreacion().isAfter(desde.minusDays(1)) && peli.getFechaDeCreacion().isBefore(hasta.plusDays(1)))
                .collect(Collectors.toList());

        List<PeliculaSerie> peliculasByQueryMethod = this.peliculaRepository.findAllByFechaDeCreacionBetween(desde, hasta);

        //THEN
        assertFalse(peliculasByQueryMethod.isEmpty());
        assertEquals(peliculaPorStream, peliculasByQueryMethod);
    }

    @Test
    @Order(5)
    void findAllByCalificacionBetween() {
        //GIVEN
        Integer desde = 3;
        Integer hasta = 4;

        List<PeliculaSerie> peliculasByStream = this.peliculaRepository.findAll()
                .stream()
                .filter(ps -> ps.getCalificacion() >= desde && ps.getCalificacion() <= hasta)
                .collect(Collectors.toList());

        //WHEN
        List<PeliculaSerie> peliculasByQueryMethod = this.peliculaRepository
                .findAllByCalificacionBetween(desde, hasta);

        //THEN
        assertEquals(peliculasByStream.size(), peliculasByQueryMethod.size());
        assertEquals(peliculasByStream, peliculasByQueryMethod);
    }
}