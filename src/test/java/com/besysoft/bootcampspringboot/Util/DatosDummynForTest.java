package com.besysoft.bootcampspringboot.Util;

import com.besysoft.bootcampspringboot.dominio.Genero;
import com.besysoft.bootcampspringboot.dominio.PeliculaSerie;
import com.besysoft.bootcampspringboot.dominio.Personaje;

import java.util.ArrayList;

public class DatosDummynForTest {

    public static Genero getGenero1(){
        return new Genero(1L, "Ciencia Ficción");
    }

    public static Genero getGenero2(){
        return new Genero(2L, "No ficción");
    }

    public static Genero getGenero3(){
        return new Genero(3L, "Documental");
    }



    public static PeliculaSerie getPelicula1(){
        return new PeliculaSerie(108L, "Ansestros Perdidos", "17-03-1999", 2, getGenero1(), new ArrayList<>());
    }

    public static PeliculaSerie getPelicula2(){
        return new PeliculaSerie(108L, "Terremoto", "17-05-1999", 4, getGenero1(), new ArrayList<>());
    }

    public static PeliculaSerie getPelicula3(){
        return new PeliculaSerie(108L, "Ansestros Perdidos 2", "17-03-2000", 5, getGenero1(), new ArrayList<>());
    }

    public static Personaje getPersonaje1(){
        return new Personaje(1L, "Jesica", 28, 70.0, "Jesica es una chica que vive sola en la casa del pueblo que sus padres le dejaron.", new ArrayList<>());
    }

    public static Personaje getPersonaje2(){
        return new Personaje(2L, "Oracio", 26, 82.4, "Oracio es un chico timido que es el cartero del pueblo.", new ArrayList<>());
    }

    public static Personaje getPersonaje3(){
        return new Personaje(6L, "Harry Potter", 10, 43.3, "Harry es un niño hurfano que fue creado por sus tios que lo desprecian por sus habilidades magicas",new ArrayList<>());
    }

}
