package com.besysoft.bootcampspringboot.services.memory.implementations;

import com.besysoft.bootcampspringboot.dominio.PeliculaSerie;
import com.besysoft.bootcampspringboot.respositories.memory.interfaces.IPeliculaSerieRepository;
import com.besysoft.bootcampspringboot.services.interfaces.IPeliculaSerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

import static com.besysoft.bootcampspringboot.utilidades.Fechas.formatear;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-data", havingValue = "memory")

public class PeliculaSerieServiceImpl implements IPeliculaSerieService {

    @Autowired
    IPeliculaSerieRepository peliculaSerieRepository;

    @Override
    public List<PeliculaSerie> obtenerTodasLasPeliculas() {
        List<PeliculaSerie> peliculas = peliculaSerieRepository.obtenerTodasLasPeliculas();
        return peliculas;
    }

    @Override
    public List<PeliculaSerie> buscarPeliculaPorTituloOGenero(String tituloOGenero) {
        if (tituloOGenero == null) {
            throw new IllegalArgumentException("El titulo o genero no puede ser nulo");
        }

        // Valida letras y numeros para peliculas con numeros.
        Boolean sonSoloLetras = tituloOGenero.matches("^[a-zA-Z0-9 ]+$");

        if (!sonSoloLetras) {
            throw new IllegalArgumentException("Ingrese un titulo o genero valido");
        }

        Optional<PeliculaSerie> OPeliculasSeries = peliculaSerieRepository.buscarTituloDePelicula(tituloOGenero);


        if (OPeliculasSeries.isEmpty()) {

            List<PeliculaSerie> peliculasPorGenero = peliculaSerieRepository.buscarPeliculaPorGenero(tituloOGenero);
            if (!peliculasPorGenero.isEmpty()) {
                return peliculasPorGenero;
            }else {
                throw new NullPointerException("No existe pelicula o genero con el nombre '"+tituloOGenero+"'");
            }
        }
        List<PeliculaSerie> listaPelicula = new ArrayList<>();
        listaPelicula.add(OPeliculasSeries.get());
        return listaPelicula;

    }

    @Override
    public List<PeliculaSerie> buscarPeliculaPorFecha(String desde, String hasta) throws DataFormatException {
        LocalDate fechaInicio = formatear(desde);
        LocalDate fechaFinal = formatear(hasta);
        if (desde.compareTo(hasta) > 0) {
            throw new DataFormatException("Rango de fecha inválido.");
        }
        List<PeliculaSerie> peliculas = peliculaSerieRepository.buscarComoRepoPeliculaPorFecha(fechaInicio,fechaFinal);

        if (peliculas.isEmpty()){
            throw new NullPointerException("No se encontro peliculas con las fechas ingresadas");
        }


        return peliculas;
    }

    @Override
    public List<PeliculaSerie> buscarPeliculasPorCalificacion(Integer desde, Integer hasta) {
        if (desde == null || hasta == null){
            throw new IllegalArgumentException("Las calificaciones no pueden ser nulas");
        }
        if (desde < 1 || desde > 5 || hasta < 1 || hasta > 5) {
            throw new IllegalArgumentException("Las calificaciones debe ser del 1 al 5");
        }
        if (!(desde<=hasta)){
            throw new IllegalArgumentException("Las calificaciones desde y hasta deben ser iguales o en orden ascendente");
        }

        List<PeliculaSerie> peliculas = peliculaSerieRepository.buscarPeliculasPorCalificaciones(desde, hasta);

        if(peliculas.isEmpty()){
            throw new NullPointerException("No se encontro peliculas con las calificaciones indicadas");
        }


        return peliculas;
    }

    @Override
    public PeliculaSerie agregarNuevaPelicula(PeliculaSerie pelicula) throws DataFormatException {

        Optional<PeliculaSerie> optionalPelicula = peliculaSerieRepository.obtenerTodasLasPeliculas()
                .stream().filter(p -> p.getTitulo().equalsIgnoreCase(pelicula.getTitulo()))
                .findAny();

        if (optionalPelicula.isPresent()) {
            throw new IllegalArgumentException("El nombre de pelicula o serie ingresado ya existe");
        }
        if (pelicula.getFechaDeCreacion() == null){
            throw new DataFormatException("La fecha de creacion no puede ser nula o estar vacia.");
        }
        if (pelicula.getTitulo().isBlank() || pelicula.getTitulo() == null) {
            throw new IllegalArgumentException("El nombre de pelicula no puede ser nulo");
        }


        Long cantidadDePeliculas = Long.valueOf(peliculaSerieRepository.obtenerTodasLasPeliculas().size());

        pelicula.setId(cantidadDePeliculas + 101);
        peliculaSerieRepository.agregarNuevaPelicula(pelicula);

        return pelicula;
    }

    @Override
    public PeliculaSerie actualizarPeliculaPorId(Long id, PeliculaSerie peliculaSerie) throws DataFormatException {
        Optional<PeliculaSerie> optionalPelicula = peliculaSerieRepository.buscarPeliculaPorId(id);

        if(optionalPelicula.isPresent()){
            PeliculaSerie pelicula = optionalPelicula.get();

            if(peliculaSerie.getTitulo().isBlank()) {
                throw new IllegalArgumentException("El titulo no puede ser nulo o estar vacio");
            }
            if(peliculaSerie.getCalificacion() == null) {
                throw new IllegalArgumentException("La calificacion no puede ser nula");
            }
            if(peliculaSerie.getFechaDeCreacion() == null) {
                throw new DataFormatException("La fecha de creacion no puede ser nula");
            }
            pelicula.setTitulo(peliculaSerie.getTitulo());
            pelicula.setCalificacion(peliculaSerie.getCalificacion());
            pelicula.setFechaDeCreacion(peliculaSerie.getFechaDeCreacion());

            return pelicula;
        } else {
            throw new NullPointerException("El id de pelicula o serie numero "+id+" no existe");
        }
    }


}
