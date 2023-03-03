package com.besysoft.bootcampspringboot.Util;

import com.besysoft.bootcampspringboot.dominio.Personaje;

import java.util.ArrayList;

public class DatosDummynPersonaje {

    public static final Personaje getPersonajeConId1(){
        return new Personaje(1L, "Jesica", 28, 70.0, "Jesica es una chica que vive sola en la casa del pueblo que sus padres le dejaron.", new ArrayList<>());
    }

    public static final Personaje getPersonajeConId2(){
        return new Personaje(2L, "Oracio", 26, 82.4, "Oracio es un chico timido que es el cartero del pueblo.", new ArrayList<>());
    }

    public static final Personaje getPersonajeConId3(){
        return new Personaje(6L, "Harry Potter", 10, 43.3, "Harry es un niño hurfano que fue creado por sus tios que lo desprecian por sus habilidades magicas",new ArrayList<>());
    }
}
