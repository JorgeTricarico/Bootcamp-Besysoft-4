package com.besysoft.bootcampspringboot.respositories.database.Interfaces;

import com.besysoft.bootcampspringboot.Util.DatosDummynForTest;
import com.besysoft.bootcampspringboot.dominio.Personaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IPersonajeRepositoryTest {

    @Autowired
    IPersonajeRepository personajeRepository;

    private final Personaje personaje1 = DatosDummynForTest.getPersonaje1();
    private final Personaje personaje2 = DatosDummynForTest.getPersonaje2();
    private final Personaje personaje3 = DatosDummynForTest.getPersonaje3();

    @BeforeEach
    void setUp() {

        personajeRepository.save(personaje1);
        personajeRepository.save(personaje2);
    }



    @Test
    void save(){

    Personaje personaje3Save = personajeRepository.save(personaje3);

    assertEquals(personaje3.getNombre(), personaje3Save.getNombre());
    }

    @Test
    void findAll(){
        List<Personaje> personajes = personajeRepository.findAll();

        //THEN
        assertFalse(personajes.isEmpty());
        assertEquals(2, personajes.size());
    }

    @Test
    void findByNombreIgnoreCase() {
        String nombrePersonajeExist = personaje1.getNombre(); //"Ansestros Perdidos";
        String nombrePeliculaUnexist = "Pelicula Inexistente";

        //WHEN
        List<Personaje> listPersonajesExits = personajeRepository.findByNombreIgnoreCase(nombrePersonajeExist);
        List<Personaje> listPersonajesUnexits = personajeRepository.findByNombreIgnoreCase(nombrePeliculaUnexist);


        //THEN
        assertFalse(listPersonajesExits.isEmpty());
        assertEquals(listPersonajesExits.get(0).getNombre(), nombrePersonajeExist);
        assertTrue(listPersonajesUnexits.isEmpty());
    }

    @Test
    void findByEdad() {
    }

    @Test
    void findByEdadBetween() {
    }
}