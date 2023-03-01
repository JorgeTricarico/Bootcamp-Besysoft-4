package com.besysoft.bootcampspringboot.respositories.database.Interfaces;

import com.besysoft.bootcampspringboot.dominio.Genero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class IGeneroRepositoryTest {

    @Autowired
    private IGeneroRepository generoRepository;


    @BeforeEach
    void setUp() {
        //GIVEN
        Genero genero = new Genero(1l, "Ciencia Ficción");
        Genero genero2 = new Genero(2L, "No ficción");
        generoRepository.save(genero);
        generoRepository.save(genero2);
    }

    @Test
    void save() {
        //GIVEN
        Genero genero3 = new Genero(3L, "Documental");

        //WHEN
        Genero generoSave = generoRepository.save(genero3);

        //THEN
        assertEquals(genero3, generoSave);
    }

    @Test
    void findAll() {

        //WHEN
        List<Genero> generos = generoRepository.findAll();

        //THEN
        assertFalse(generos.isEmpty());
        assertEquals(2, generos.size());
    }



    @Test
    void existsGeneroById() {

        //WHEN
        Boolean existGenero = generoRepository.existsGeneroById(1L);
        Boolean unexistGenero = generoRepository.existsGeneroById(11111111111111L);

        //THEN
        assertTrue(existGenero);
        assertFalse(unexistGenero);

    }

    @Test
    void findGeneroByNombreIgnoreCase() {

        Optional oGeneroExist = generoRepository.findGeneroByNombreIgnoreCase("Ciencia Ficción");
        Genero generoExist = (Genero) oGeneroExist.get();
        Optional oGeneroUnexist = generoRepository.findGeneroByNombreIgnoreCase("Genero Inexistente");

        assertTrue(oGeneroExist.isPresent());
        assertTrue(generoExist.getNombre().equalsIgnoreCase("Ciencia Ficción"));
        assertTrue(oGeneroUnexist.isEmpty());

    }

    @Test
    void existsGeneroByNombreIgnoreCase() {

        //WHEN
        Boolean existGenero = generoRepository.existsGeneroByNombreIgnoreCase("Ciencia Ficción");
        Boolean unexistGenero = generoRepository.existsGeneroByNombreIgnoreCase("Nombre de genero que no existe");

        //THEN
        assertTrue(existGenero);
        assertFalse(unexistGenero);
    }
}