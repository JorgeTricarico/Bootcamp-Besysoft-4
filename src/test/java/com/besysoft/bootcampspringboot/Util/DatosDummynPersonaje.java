package com.besysoft.bootcampspringboot.Util;

import com.besysoft.bootcampspringboot.dominio.Personaje;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatosDummynPersonaje {

    public static final List<Personaje> LISTA_PERSONAJES = new ArrayList<>(Arrays.asList(getPersonajeConId1(),getPersonajeConId2(),getPersonajeConId3()));

    public static final Personaje getPersonajeConId1(){
        return new Personaje(1L, "Jesica", 28, 70.0, "Jesica es una chica que vive sola en la casa del pueblo que sus padres le dejaron.", null);
    }

    public static final Personaje getPersonajeConId2(){
        return new Personaje(2L, "Oracio", 26, 82.4, "Oracio es un chico timido que es el cartero del pueblo.", null);
    }

    public static final Personaje getPersonajeConId3(){
        return new Personaje(6L, "Harry Potter", 10, 43.3, "Harry es un niño hurfano que fue creado por sus tios que lo desprecian por sus habilidades magicas",null);
    }
}
