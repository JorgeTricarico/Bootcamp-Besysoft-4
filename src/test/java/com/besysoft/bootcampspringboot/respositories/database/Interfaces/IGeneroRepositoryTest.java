package com.besysoft.bootcampspringboot.respositories.database.Interfaces;

import com.besysoft.bootcampspringboot.dominio.Genero;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import static com.besysoft.bootcampspringboot.Util.DatosDummynGenero.*;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IGeneroRepositoryTest {

    private final Genero genero1 = getGeneroSinId1();
    private final Genero genero2 = getGeneroSinId2();

    @Autowired
    private IGeneroRepository generoRepository;


    @BeforeAll
    static void beforeAll() {

    }

    @BeforeEach
    void setUp() {
        //GIVEN GLOBAL. Se carga data a travez de import.sql solo para el test en H2.

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    @Order(1)
    void save() {
        //GIVEN
        Genero genero3 = getGeneroSinId3();

        //WHEN
        Genero generoSave = generoRepository.save(genero3);

        //THEN
        assertEquals(genero3.getNombre(), generoSave.getNombre());

        assertEquals(genero3, generoSave);

        List<Genero> listaGenero = generoRepository.findAll();
        System.out.println(listaGenero);
    }

    @Test
    @Order(2)
    void findAll() {
        //Given proporcionado por la base previamente carga en H2

        //WHEN
        List<Genero> generos = generoRepository.findAll();

        //THEN
        assertFalse(generos.isEmpty());
        assertEquals(4, generos.size());

        System.out.println(generos);
    }
    @Test
    @Order(3)
    void existsGeneroById() {
        //Given
        Genero gerneroSave1 = generoRepository.save(genero1);
        Long idExist = gerneroSave1.getId();
        Long idUnxist = 11111111111111L;

        //WHEN
        Boolean existGenero = generoRepository.existsGeneroById(idExist);
        Boolean unexistGenero = generoRepository.existsGeneroById(idUnxist);

        //THEN
        assertFalse(generoRepository.findAll().isEmpty());

        assertTrue(existGenero);
        assertFalse(unexistGenero);
    }

    @Test
    @Order(4)
    void findGeneroByNombreIgnoreCase() {

        Genero gerneroSave1 = generoRepository.save(genero1);

        //WHEN
        Optional<Genero> oGeneroExist = generoRepository.findGeneroByNombreIgnoreCase("Ciencia Ficción");
        Genero generoExist = oGeneroExist.get();
        Optional oGeneroUnexist = generoRepository.findGeneroByNombreIgnoreCase("Genero Inexistente");

        //THEN
        assertTrue(oGeneroExist.isPresent());
        assertTrue(generoExist.getNombre().equalsIgnoreCase("Ciencia Ficción"));
        assertTrue(oGeneroUnexist.isEmpty());
    }
    @Test
    @Order(5)
    void existsGeneroByNombreIgnoreCase() {
        //Given
        Genero gerneroSave1 = generoRepository.save(genero1);

        //GIVEN
        String nameExist = gerneroSave1.getNombre();
        String nameUnxist = "Nombre de genero1 que no existe";

        //WHEN
        Boolean existGenero = generoRepository.existsGeneroByNombreIgnoreCase(nameExist);
        Boolean unexistGenero = generoRepository.existsGeneroByNombreIgnoreCase(nameUnxist);

        //THEN
        assertTrue(existGenero);
        assertFalse(unexistGenero);
    }
}