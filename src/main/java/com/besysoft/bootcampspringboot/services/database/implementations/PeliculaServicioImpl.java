package com.besysoft.bootcampspringboot.services.database.implementations;

import com.besysoft.bootcampspringboot.dominio.PeliculaSerie;
import com.besysoft.bootcampspringboot.respositories.database.Interfaces.IPeliculaSerieRepository;
import com.besysoft.bootcampspringboot.services.interfaces.IPeliculaSerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

import static com.besysoft.bootcampspringboot.utilidades.Fechas.formatear;
import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.badResquest;
import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.notFound;
import static com.besysoft.bootcampspringboot.utilidades.Validaciones.validarLetrasYNumeros;

@ConditionalOnProperty(prefix = "app", name = "type-data", havingValue = "database")
@Service

public class PeliculaServicioImpl implements IPeliculaSerieService {

    @Autowired
    IPeliculaSerieRepository peliculaRepository;
    @Override
    @Transactional(readOnly = true)
    public List<PeliculaSerie> obtenerTodasLasPeliculas() {
        List<PeliculaSerie> peliculas = peliculaRepository.findAll();
        if(peliculas.isEmpty()){
            throw new NullPointerException("No hay peliculas en la base de datos.");
        }
        return peliculas;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PeliculaSerie> buscarPeliculaPorTituloOGenero(String tituloOGenero) {
        validarLetrasYNumeros("titulo o genero", tituloOGenero);

        List<PeliculaSerie> peliculasPorGenero = peliculaRepository.findAll().stream()
                .filter(peliculaSerie ->  peliculaSerie.getGenero().getNombre().equalsIgnoreCase(tituloOGenero))
                .collect(Collectors.toList());
        if (!peliculasPorGenero.isEmpty()) {
            return peliculasPorGenero;
        }

        Optional<PeliculaSerie> OPeliculasSeries = peliculaRepository.findByTituloIgnoreCase(tituloOGenero);

        if (OPeliculasSeries.isPresent()){
            return OPeliculasSeries.stream().collect(Collectors.toList());
        }else{
            throw new NullPointerException("No existe pelicula o genero con el nombre '" + tituloOGenero + "'.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PeliculaSerie> buscarPeliculaPorFecha(String desde, String hasta) throws DataFormatException {
        LocalDate fechaInicio = formatear(desde);
        LocalDate fechaFinal = formatear(hasta);

        List<PeliculaSerie> peliculas = peliculaRepository.findAllByFechaDeCreacionBetween(fechaInicio,fechaFinal);

        if (peliculas.isEmpty()){
            throw new NullPointerException("No se encontro peliculas con las fechas ingresadas");
        }
        return peliculas;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PeliculaSerie> buscarPeliculasPorCalificacion(Integer desde, Integer hasta) {

        List<PeliculaSerie> peliculas = peliculaRepository.findAllByCalificacionBetween(desde, hasta);

        if(peliculas.isEmpty()){
            throw  new NullPointerException("No se encontro peliculas calificadas de "+desde + "a "+ hasta + ".");
        }

        return peliculas;
    }

    @Override
    @Transactional(readOnly = false)
    public PeliculaSerie agregarNuevaPelicula(PeliculaSerie pelicula) {


        Optional<PeliculaSerie> optionalPelicula = peliculaRepository.findByTituloIgnoreCase(pelicula.getTitulo());//obtenerTodasLasPeliculas().stream().filter(p -> p.getTitulo().equalsIgnoreCase(pelicula.getTitulo())).findAny();

        if (optionalPelicula.isPresent()) {
            throw new IllegalArgumentException("El nombre de la pelicula o serie '"+pelicula.getTitulo()+"' ya existe");
        }

        peliculaRepository.save(pelicula);

        return pelicula;
    }

    @Override
    @Transactional(readOnly = false)
    public PeliculaSerie actualizarPeliculaPorId(Long id, PeliculaSerie peliculaSerie) {
        Optional<PeliculaSerie> optionalPelicula = peliculaRepository.findById(id);

        if (optionalPelicula.isPresent()) {
            PeliculaSerie pelicula = optionalPelicula.get();

            peliculaSerie.setId(id);
            return peliculaRepository.save(peliculaSerie);

            //return peliculaSerie;
        } else {
            throw new NullPointerException("El id ingresado numero '"+id+"'  no existe en la base de datos");
        }

    }

}
