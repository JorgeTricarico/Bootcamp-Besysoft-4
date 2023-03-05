package com.besysoft.bootcampspringboot.Util;

import com.besysoft.bootcampspringboot.dominio.PeliculaSerie;
import com.besysoft.bootcampspringboot.dominio.Personaje;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.besysoft.bootcampspringboot.Util.DatosDummynGenero.*;

public class DatosDummynPelicula {

    public static final List<PeliculaSerie> LISTA_PELICULAS = new ArrayList<>(Arrays.asList(getPeliculaConId1(),getPeliculaConId2(),getPeliculaConId3()));
    public static final PeliculaSerie getPeliculaConId1(){
        return new PeliculaSerie(108L, "Ansestros Perdidos", "17-03-1999", 2, getGeneroSinId1(), null);
    }

    public static final PeliculaSerie getPeliculaConId2(){
        return new PeliculaSerie(108L, "Terremoto", "17-05-1999", 4, getGeneroSinId1(), null);
    }

    public static final PeliculaSerie getPeliculaConId3(){
        return new PeliculaSerie(108L, "Ansestros Perdidos 2", "17-03-2000", 5, getGeneroSinId1(), null);
    }
}
