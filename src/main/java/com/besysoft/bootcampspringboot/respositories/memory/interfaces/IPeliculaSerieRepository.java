package com.besysoft.bootcampspringboot.respositories.memory.interfaces;

import com.besysoft.bootcampspringboot.dominio.Genero;
import com.besysoft.bootcampspringboot.dominio.PeliculaSerie;
import com.besysoft.bootcampspringboot.dominio.Personaje;
import com.besysoft.bootcampspringboot.utilidades.DatoDummyn;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.besysoft.bootcampspringboot.utilidades.DatoDummyn.buscarComoRepoGeneroPorID;

public interface IPeliculaSerieRepository {

    static List<PeliculaSerie> crearPeliculaSerie() {


        Personaje jesica = new Personaje(10001L, "Jesica", 28, 70.0, "Jesica es una chica que vive sola en la casa del pueblo que sus padres le dejaron.", new ArrayList<>());
        Personaje oracio = new Personaje(10002L, "Oracio", 26, 82.4, "Oracio es un chico timido que es el cartero del pueblo.", new ArrayList<>());

        Personaje harry = new Personaje(10006L, "Harry Potter", 10, 43.3, "Harry es un niño hurfano que fue creado por sus tios que lo desprecian por sus habilidades magicas",new ArrayList<>());
        Personaje ron = new Personaje(10007L, "Ron Haswich", 10, 45.6, "Ron es un niño torpe y despistado, hijo de padres magos que lo aman", new ArrayList<>());
        Personaje hermanione = new Personaje(10008L, "Hermione Jean Granger", 10, 39.9, "Hermione es una niña hija de muggles dentistas, sin embargo ella es maga y asiste ala escuela de magia", new ArrayList<>());

        Personaje la_roca = new Personaje(10003L, "La Roca", 45, 102.8, "Un tipo rudo que no obedece las reglas, su vida fue siempre muy dura y no le teme al peligro", new ArrayList<>());
        Personaje hernestoPalacio = new Personaje(10004L, "Hernesto Palacio", 40, 78.7, "Un hombre comun que viene ed una familia de clase media. Nadacido en Mexico y siempre se mete en problemas", new ArrayList<>());
        Personaje keyti = new Personaje(10005L, "Keyti", 35, 68.5, "Una chica rebelde que no obedece las reglas, nacida en Miami de familia, es mecanica de niña", new ArrayList<>());


        Personaje lisandra = new Personaje(10009L, "Lisandra", 16, 46.7, "Lisandra es una esclava carismatica y hermosa, hija de padres esclavos rebeldes", new ArrayList<>());
        Personaje lisandro = new Personaje(10010L, "Lisandro", 18, 58.7, "Lisandro es una esclavo rudo y hermoso, hijo de padres esclavos rebeldes", new ArrayList<>());
        Personaje hernestoHernandez = new Personaje(10011L, "Hernesto Hernandez", 50, 86.6, "Hernesto es un esclavista viudo  y cruel con sus esclavos", new ArrayList<>());
        Personaje isaias = new Personaje(10012L, "Isaias Norting", 48, 83.1, "Isaias es un esclavista hermoso y bueno con sus esclavos", new ArrayList<>());

        Personaje lisa = new Personaje(10013L, "Lisa", 13, 38.2, "Lisa es un huerfana retraida y timida poseida por un ser maligno", new ArrayList<>());

        Personaje julio = new Personaje(10014L, "Julio", 39, 89.7, "Julio es un hombre solitario que le gusta ver sufrir a las personas", new ArrayList<>());


        // Peliculas

        PeliculaSerie elTemblor1 = new PeliculaSerie(101L, "El Temblor 1", "27-02-2018", 3, buscarComoRepoGeneroPorID(1L).get() , new ArrayList<>(Arrays.asList(
                jesica, oracio)));
        PeliculaSerie elTemblor2 = new PeliculaSerie(102L, "El Temblor 2", "17-03-2021", 2, buscarComoRepoGeneroPorID(1L).get(), new ArrayList<>(Arrays.asList(
                jesica, oracio)));

        PeliculaSerie harryPotter1 = new PeliculaSerie(103L, "Harry Potter 1", "17-03-2003", 4, buscarComoRepoGeneroPorID(2L).get(), new ArrayList<>(Arrays.asList(
                harry, ron, hermanione)));
        PeliculaSerie harryPotter2 = new PeliculaSerie(104L, "Harry Potter 2", "17-03-2005", 3, buscarComoRepoGeneroPorID(2L).get(), new ArrayList<>(Arrays.asList(
                harry, ron, hermanione)));
        PeliculaSerie harryPotter3 = new PeliculaSerie(105L, "Harry Potter 3", "17-03-2007", 4, buscarComoRepoGeneroPorID(2L).get(), new ArrayList<>(Arrays.asList(
                harry, ron, hermanione)));
        PeliculaSerie harryPotter4 = new PeliculaSerie(106L, "Harry Potter 4", "17-03-2009", 4, buscarComoRepoGeneroPorID(2L).get(), new ArrayList<>(Arrays.asList(
                harry, ron, hermanione)));
        PeliculaSerie harryPotter5 = new PeliculaSerie(107L, "Harry Potter 5", "17-03-2012", 5, buscarComoRepoGeneroPorID(2L).get(), new ArrayList<>(Arrays.asList(
                harry, ron, hermanione)));

        PeliculaSerie rapidoYFurioso1 = new PeliculaSerie(108L, "Rapido y Furioso 1", "17-03-1999", 5, buscarComoRepoGeneroPorID(3L).get(), new ArrayList<>(Arrays.asList(
                la_roca, hernestoPalacio, keyti)));
        PeliculaSerie rapidoYFurioso2 = new PeliculaSerie(109L, "Rapido y Furioso 2", "19-06-2003", 3,buscarComoRepoGeneroPorID(3L).get(),  new ArrayList<>(Arrays.asList(
                la_roca, hernestoPalacio, keyti)));
        PeliculaSerie rapidoYFurioso3 = new PeliculaSerie(110L, "Rapido y Furioso 3", "07-01-2008", 3, buscarComoRepoGeneroPorID(3L).get(), new ArrayList<>(Arrays.asList(
                la_roca, hernestoPalacio, keyti)));
        PeliculaSerie rapidoYFurioso4 = new PeliculaSerie(111L, "Rapido y Furioso 4", "17-03-2009", 2,buscarComoRepoGeneroPorID(3L).get(),  new ArrayList<>(Arrays.asList(
                la_roca, hernestoPalacio, keyti)));
        PeliculaSerie rapidoYFurioso30 = new PeliculaSerie(112L, "Rapido y Furioso 30", "17-03-2015", 1, buscarComoRepoGeneroPorID(3L).get(), new ArrayList<>(Arrays.asList(
                la_roca, hernestoPalacio, keyti)));

        PeliculaSerie laEsclava = new PeliculaSerie(113L, "La Esclava", "25-09-2015", 2, buscarComoRepoGeneroPorID(1L).get(), new ArrayList<>(Arrays.asList(
                lisandra, lisandro, hernestoHernandez, isaias)));
        PeliculaSerie laHuerfana1 = new PeliculaSerie(114L, "La Huerfana 1", "01-08-2003", 4, buscarComoRepoGeneroPorID(4L).get(), new ArrayList<>(Arrays.asList(
                lisa)));
        PeliculaSerie laHuerfana2 = new PeliculaSerie(115L, "La Huerfana 2", "09-07-2005", 5, buscarComoRepoGeneroPorID(4L).get(), new ArrayList<>(Arrays.asList(
                lisa)));
        PeliculaSerie laHuerfana3 = new PeliculaSerie(116L, "La Huerfana 3", "17-02-2010", 3, buscarComoRepoGeneroPorID(4L).get(), new ArrayList<>(Arrays.asList(
                lisa)));
        PeliculaSerie laHuerfana4 = new PeliculaSerie(117L, "La Huerfana 4", "29-07-2013", 4, buscarComoRepoGeneroPorID(4L).get(), new ArrayList<>(Arrays.asList(
                lisa)));

        PeliculaSerie elJuegoDelMiedo1 = new PeliculaSerie(118L, "El Juego del Miedo 1", "15-04-2001", 5, buscarComoRepoGeneroPorID(4L).get(), new ArrayList<>(Arrays.asList(
                julio)));
        PeliculaSerie elJuegoDelMiedo2 = new PeliculaSerie(119L, "El Juego del Miedo 2", "17-08-2008", 4, buscarComoRepoGeneroPorID(4L).get(), new ArrayList<>(Arrays.asList(
                julio)));
        PeliculaSerie elJuegoDelMiedo3 = new PeliculaSerie(120L, "El Juego del Miedo 3", "17-09-2014", 3, buscarComoRepoGeneroPorID(4L).get(), new ArrayList<>(Arrays.asList(
                julio)));
        PeliculaSerie elJuegoDelMiedo4 = new PeliculaSerie(121L, "El Juego del Miedo 4", "17-11-2018", 5, buscarComoRepoGeneroPorID(4L).get(), new ArrayList<>(Arrays.asList(
                julio)));



        List<PeliculaSerie> listaDePeliculas = new ArrayList<>(Arrays.asList(
                elTemblor1,elTemblor2,harryPotter1,harryPotter2,harryPotter3,harryPotter4,harryPotter5,rapidoYFurioso1,rapidoYFurioso2,rapidoYFurioso3,rapidoYFurioso4,rapidoYFurioso30,
                laEsclava, laHuerfana1,laHuerfana2,laHuerfana3,laHuerfana4,elJuegoDelMiedo1,elJuegoDelMiedo2,elJuegoDelMiedo3,elJuegoDelMiedo4));

        return listaDePeliculas;
    }

    List<PeliculaSerie> obtenerTodasLasPeliculas();

    List<PeliculaSerie> buscarPeliculaPorGenero (String nombreDeGenero);
    List<PeliculaSerie> buscarPeliculasPorCalificaciones(Integer desde, Integer hasta);

    List<PeliculaSerie> buscarComoRepoPeliculaPorFecha(LocalDate fechaInicio, LocalDate fechaFinal);

    void agregarNuevaPelicula(PeliculaSerie peliculaSerie);

    Optional<PeliculaSerie> buscarTituloDePelicula(String titulo);
    Optional<PeliculaSerie> buscarPeliculaPorId(Long id);


}
