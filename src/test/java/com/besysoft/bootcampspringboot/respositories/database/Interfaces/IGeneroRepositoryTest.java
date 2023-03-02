package com.besysoft.bootcampspringboot.respositories.database.Interfaces;

import com.besysoft.bootcampspringboot.Util.DatosDummynForTest;
import com.besysoft.bootcampspringboot.dominio.Genero;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class IGeneroRepositoryTest {

    private final Genero genero = DatosDummynForTest.getGenero1();
    private final Genero genero2 = DatosDummynForTest.getGenero2();

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
    void save() {
        //GIVEN
        //Genero genero3 = DatosDummynForTest.getGenero3();

        //WHEN
        //Genero generoSave = generoRepository.save(genero3);

        //THEN
        //assertEquals(genero3.getNombre(), generoSave.getNombre());

        //assertEquals(genero3, generoSave); ///
    }

    @Test
    void findAll() {
        //Given
        Genero gerneroSave1 = generoRepository.save(genero);
        Genero gerneroSave2 = generoRepository.save(genero2);

        //WHEN
        List<Genero> generos = generoRepository.findAll();

        //THEN
        assertFalse(generos.isEmpty());
        assertEquals(4, generos.size());
    }



    @Test
    void existsGeneroById() {

        //Given
        Genero gerneroSave1 = generoRepository.save(genero);
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
    void findGeneroByNombreIgnoreCase() {

        Genero gerneroSave1 = generoRepository.save(genero);
        Genero gerneroSave2 = generoRepository.save(genero2);


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

    void existsGeneroByNombreIgnoreCase() {

        Genero gerneroSave1 = generoRepository.save(genero);
        Genero gerneroSave2 = generoRepository.save(genero2);

        //GIVEN
        String nameExist = "Ciencia Ficción";
        String nameUnxist = "Nombre de genero que no existe";

        //WHEN
        Boolean existGenero = generoRepository.existsGeneroByNombreIgnoreCase(nameExist);
        Boolean unexistGenero = generoRepository.existsGeneroByNombreIgnoreCase(nameUnxist);

        //THEN
        assertTrue(existGenero);
        assertFalse(unexistGenero);
    }
}