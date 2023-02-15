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

import static com.besysoft.bootcampspringboot.utilidades.Fechas.formatear;
import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.badResquest;
import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.notFound;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-data", havingValue = "database")
public class PeliculaServicioImpl implements IPeliculaSerieService {

    @Autowired
    IPeliculaSerieRepository peliculaRepository;
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> obtenerTodasLasPeliculas() {
        List<PeliculaSerie> peliculas = peliculaRepository.findAll();
        return new ResponseEntity(peliculas, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> buscarPeliculaPorTituloOGenero(String tituloOGenero) {
        if (tituloOGenero.isBlank()) {
            return badResquest("El titulo o genero no puede ser nulo");
        }

        // Valida letras y numeros para peliculas con numeros.
        Boolean sonSoloLetras = tituloOGenero.matches("^[a-zA-Z0-9 ]+$");

        if (!sonSoloLetras) {
            return badResquest("Ingrese un titulo o genero valido");
        }

        Optional<PeliculaSerie> OPeliculasSeries = peliculaRepository.findByTituloIgnoreCase(tituloOGenero);


        if (OPeliculasSeries.isEmpty()) {
            //return DatoDummyn.notFound("El titulo o genero %s no fue encontrado",titulo);
            List<PeliculaSerie> peliculasPorGenero = peliculaRepository.findAll().stream()
                    .filter(peliculaSerie ->  peliculaSerie.getGenero().getNombre().equalsIgnoreCase(tituloOGenero))
                    .collect(Collectors.toList());

            if (!peliculasPorGenero.isEmpty()){
                return new ResponseEntity<>(peliculasPorGenero, HttpStatus.OK);
            }else {
                return notFound("No existe pelicula o genero con el nombre '%s'.", tituloOGenero);
            }
        }

        return new ResponseEntity<>(OPeliculasSeries.get(), HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> buscarPeliculaPorFecha(String desde, String hasta) {
        LocalDate fechaInicio = formatear(desde);
        LocalDate fechaFinal = formatear(hasta);
        if (desde.compareTo(hasta) > 0) {
            return badResquest("Rango de fecha inválido.");
        }
        List<PeliculaSerie> oPeliculas = peliculaRepository.findAllByFechaDeCreacionBetween(fechaInicio,fechaFinal);

        if (oPeliculas.isEmpty()){
            return notFound("No se encontro peliculas con las fechas ingresadas");
        }


        return new ResponseEntity(oPeliculas, HttpStatus.ACCEPTED);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> buscarPeliculasPorCalificacion(Integer desde, Integer hasta) {
        if (desde == null || hasta == null){
            return badResquest("Las calificaciones no pueden ser nulas");
        }
        if (desde < 1 || desde > 5 || hasta < 1 || hasta > 5) {
            return badResquest("Las calificaciones debe ser del 1 al 5");
        }
        if (!(desde<=hasta)){
            return badResquest("Las calificaciones desde y hasta deben ser iguales o en orden ascendente");
        }

        List<PeliculaSerie> peliculas = peliculaRepository.findAllByCalificacionBetween(desde, hasta);

        if(peliculas.isEmpty()){
            return notFound("No se encontro peliculas con las calificaciones indicadas");
        }


        return new ResponseEntity(peliculas, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = false)
    public ResponseEntity agregarNuevaPelicula(PeliculaSerie pelicula) {

        if (pelicula.getTitulo().isBlank() || pelicula.getTitulo() == null) {
            return badResquest("El nombre de pelicula no puede ser nulo");
        }

        Optional<PeliculaSerie> optionalPelicula = peliculaRepository.findByTituloIgnoreCase(pelicula.getTitulo());//obtenerTodasLasPeliculas().stream().filter(p -> p.getTitulo().equalsIgnoreCase(pelicula.getTitulo())).findAny();

        if (optionalPelicula.isPresent()) {
            return badResquest("El nombre de pelicula o serie '%s' ingresado ya existe", pelicula.getTitulo());
        }
        if (pelicula.getFechaDeCreacion() == null){
            return badResquest("La fecha de creacion no puede ser nula o estar vacia.");
        }
        if (pelicula.getTitulo().isBlank() || pelicula.getTitulo() == null) {
            return badResquest("El nombre de pelicula no puede ser nulo");
        }


        //Long cantidadDePeliculas = Long.valueOf(peliculaRepository.findAll().size());
        //pelicula.setId(cantidadDePeliculas + 101);
        peliculaRepository.save(pelicula);

        return new ResponseEntity(pelicula, HttpStatus.CREATED);
    }

    @Override
    @Transactional(readOnly = false)
    public ResponseEntity actualizarPeliculaPorId(Long id, PeliculaSerie peliculaSerie) {
        Optional<PeliculaSerie> optionalPelicula = peliculaRepository.findById(id);

        if (optionalPelicula.isPresent()) {
            PeliculaSerie pelicula = optionalPelicula.get();

            if (peliculaSerie.getTitulo().isBlank()) {
                return badResquest("El titulo no puede ser nulo o estar vacio");
            }
            if (peliculaSerie.getCalificacion() == null || peliculaSerie.getCalificacion() > 5 || peliculaSerie.getCalificacion() < 1) {
                return badResquest("La calificacion no puede ser nula y tiene que estar entre 1 y 5");
            }
            if (peliculaSerie.getFechaDeCreacion() == null) {
                return badResquest("La fecha de creacion no puede ser nula");
            }
            if (peliculaSerie.getFechaDeCreacion().isAfter(LocalDate.now())) {
                return badResquest("La fecha no puede ser del futuro.");
            }


            peliculaSerie.setId(id);
            peliculaRepository.save(peliculaSerie);

            return new ResponseEntity(pelicula, HttpStatus.OK);

        } else {
            return notFound("El id %s ingresado no existe", id);
        }

    }

}
