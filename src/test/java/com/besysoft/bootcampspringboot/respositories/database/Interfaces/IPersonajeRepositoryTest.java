package com.besysoft.bootcampspringboot.respositories.database.Interfaces;

import com.besysoft.bootcampspringboot.dominio.Personaje;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.besysoft.bootcampspringboot.Util.DatosDummynPersonaje.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IPersonajeRepositoryTest {

    @Autowired
    IPersonajeRepository personajeRepository;

    private final Personaje personaje1 = getPersonajeConId1();
    private final Personaje personaje2 = getPersonajeConId2();
    private final Personaje personaje3 = getPersonajeConId3();

    @BeforeEach
    void setUp() {

        personajeRepository.save(personaje1);
        personajeRepository.save(personaje2);
    }
    @Test
    @Order(1)
    void save(){

    Personaje personaje3Save = personajeRepository.save(personaje3);

    assertEquals(personaje3.getNombre(), personaje3Save.getNombre());
    }

    @Test
    @Order(2)
    void findAll(){
        List<Personaje> personajes = personajeRepository.findAll();

        //THEN
        assertFalse(personajes.isEmpty());
        assertEquals(14, personajes.size());
    }

    @Test
    @Order(3)
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
    @Order(4)
    void findByEdad() {
    }

    @Test
    @Order(5)
    void findByEdadBetween() {
    }
}