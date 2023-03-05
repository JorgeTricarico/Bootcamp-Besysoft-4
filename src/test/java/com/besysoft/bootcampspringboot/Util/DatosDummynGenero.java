package com.besysoft.bootcampspringboot.Util;

import com.besysoft.bootcampspringboot.dominio.Genero;
import com.besysoft.bootcampspringboot.respositories.database.Interfaces.IGeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class DatosDummynGenero {

    @Autowired
    IGeneroRepository generoRepository;

    public static final List<Genero> LISTA_GENERO_SIN_ID = new ArrayList<>(Arrays.asList(
            new Genero(null, "Ciencia Ficción"),
            new Genero(null, "No ficción"),
            new Genero(null, "Documental")));


    public static  final Genero getGeneroSinId1(){
        return new Genero(null, "Ciencia Ficcion");
    }

    public static final Genero getGeneroSinId2(){
        return new Genero(null, "No ficcion");
    }

    public static final Genero getGeneroSinId3(){
        return new Genero(null, "Documental");
    }

    public static final Genero getGeneroConId(){
        return new Genero(1L, "Policial");
    }



}
