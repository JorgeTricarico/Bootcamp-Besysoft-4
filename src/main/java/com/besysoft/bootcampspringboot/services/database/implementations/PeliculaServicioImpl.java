package com.besysoft.bootcampspringboot.services.database.implementations;

import com.besysoft.bootcampspringboot.DTO.Mapper.IPeliculaSerieMapper;
import com.besysoft.bootcampspringboot.DTO.Request.PeliculaSerieRequestDto;
import com.besysoft.bootcampspringboot.DTO.Response.PeliculaSerieResponseDto;
import com.besysoft.bootcampspringboot.Exception.GeneroNotFoundException;
import com.besysoft.bootcampspringboot.Exception.InvalidValueException;
import com.besysoft.bootcampspringboot.Exception.PeliculaAlreadyExistsException;
import com.besysoft.bootcampspringboot.Exception.PeliculaNotFoundException;
import com.besysoft.bootcampspringboot.dominio.Genero;
import com.besysoft.bootcampspringboot.dominio.PeliculaSerie;
import com.besysoft.bootcampspringboot.respositories.database.Interfaces.IGeneroRepository;
import com.besysoft.bootcampspringboot.respositories.database.Interfaces.IPeliculaSerieRepository;
import com.besysoft.bootcampspringboot.services.interfaces.IPeliculaSerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.besysoft.bootcampspringboot.utilidades.Fechas.formatear;
import static com.besysoft.bootcampspringboot.utilidades.Validaciones.validarLetrasYNumeros;

@ConditionalOnProperty(prefix = "app", name = "type-data", havingValue = "database")
@Service

public class PeliculaServicioImpl implements IPeliculaSerieService {

    @Autowired
    IPeliculaSerieRepository peliculaRepository;
    @Autowired
    IGeneroRepository generoRepository;
    @Autowired
    IPeliculaSerieMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<PeliculaSerieResponseDto> obtenerTodasLasPeliculas() {
        List<PeliculaSerie> peliculas = peliculaRepository.findAll();
        if (peliculas.isEmpty()) {
            throw new PeliculaNotFoundException("No hay ninguna pelicula en la base de datos");
        }
        List<PeliculaSerieResponseDto> peliculaSerieResponseDtos = peliculas.stream()
                .map(pelicula -> mapper
                        .mapToDto(pelicula))
                .collect(Collectors.toList());

        return peliculaSerieResponseDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PeliculaSerieResponseDto> buscarPeliculaPorTituloOGenero(String tituloOGenero) {
        validarLetrasYNumeros("titulo o genero", tituloOGenero);

        List<PeliculaSerie> peliculasPorGenero = peliculaRepository.findAll()
                .stream()
                .filter(peliculaSerie -> peliculaSerie.getGenero()
                        .getNombre().equalsIgnoreCase(tituloOGenero))
                .collect(Collectors.toList());
        if (!peliculasPorGenero.isEmpty()) {

            List<PeliculaSerieResponseDto> peliculaSerieResponseDtos = peliculasPorGenero.stream()
                    .map(pelicula -> mapper.
                            mapToDto(pelicula))
                    .collect(Collectors.toList());

            return peliculaSerieResponseDtos;
        }

        Optional<PeliculaSerie> OPeliculasSeries = peliculaRepository.findByTituloIgnoreCase(tituloOGenero);

        if (OPeliculasSeries.isPresent()) {
            return OPeliculasSeries.stream()
                    .map(pelicula -> mapper
                            .mapToDto(pelicula))
                    .collect(Collectors.toList());//OPeliculasSeries.stream().collect(Collectors.toList());
        } else {
            throw new PeliculaNotFoundException("No existe pelicula con el titulo o nombre de genero llamado '" + tituloOGenero);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PeliculaSerieResponseDto> buscarPeliculaPorFecha(String desde, String hasta){
        LocalDate fechaInicio = formatear(desde);
        LocalDate fechaFinal = formatear(hasta);

        List<PeliculaSerie> peliculas = peliculaRepository
                .findAllByFechaDeCreacionBetween(fechaInicio, fechaFinal);

        if (peliculas.isEmpty()) {
            throw new PeliculaNotFoundException("No se encontro peliculas con las fechas ingresadas");
        }

        List<PeliculaSerieResponseDto> peliculaSerieResponseDtos = peliculas.stream()
                .map(pelicula -> mapper
                        .mapToDto(pelicula))
                .collect(Collectors.toList());
        return peliculaSerieResponseDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PeliculaSerieResponseDto> buscarPeliculasPorCalificacion(Integer desde, Integer hasta) {

        List<PeliculaSerie> peliculas = peliculaRepository.findAllByCalificacionBetween(desde, hasta);

        if (peliculas.isEmpty()) {
            throw new PeliculaNotFoundException("No se encontro peliculas calificadas de " + desde + "a " + hasta + ".");
        }
        List<PeliculaSerieResponseDto> peliculaSerieResponseDtos = peliculas.stream()
                .map(pelicula -> mapper
                        .mapToDto(pelicula))
                .collect(Collectors.toList());

        return peliculaSerieResponseDtos;
    }

    @Override
    @Transactional(readOnly = false)
    public PeliculaSerieResponseDto agregarNuevaPelicula(PeliculaSerieRequestDto peliculaRequestDto) {
        Optional<PeliculaSerie> optionalPelicula = peliculaRepository.findByTituloIgnoreCase(peliculaRequestDto.getTitulo());//obtenerTodasLasPeliculas().stream().filter(p -> p.getTitulo().equalsIgnoreCase(pelicula.getTitulo())).findAny();

        if (optionalPelicula.isPresent()) {
            throw new PeliculaAlreadyExistsException("El nombre de la pelicula o serie '" + peliculaRequestDto.getTitulo() + "' ya existe");
        }
        System.out.println(peliculaRequestDto);

        String nombreDeGenero = peliculaRequestDto.getNombreGenero();

        Optional<Genero> optionalGenero = generoRepository
                .findGeneroByNombreIgnoreCase(nombreDeGenero);

        if(optionalGenero.isPresent()) {

            PeliculaSerie pelicula = mapper.mapToEntity(peliculaRequestDto);
            pelicula.setGenero(optionalGenero.get());
            PeliculaSerie peliculaSave = peliculaRepository.save(pelicula);
            PeliculaSerieResponseDto peliculaResponseDto = mapper.mapToDto(peliculaSave);
            return peliculaResponseDto;

        }else {
            throw new GeneroNotFoundException("El genero '" + nombreDeGenero + "' no existe");
        }


    }

    @Override
    @Transactional(readOnly = false)
    public PeliculaSerieResponseDto actualizarPeliculaPorId(Long id, PeliculaSerieRequestDto peliculaRequestDto) {
        Optional<PeliculaSerie> optionalPelicula = peliculaRepository.findById(id);

        if (optionalPelicula.isPresent()) {
            String nombreDeGenero = peliculaRequestDto.getNombreGenero();

            Optional<Genero> optionalGenero = generoRepository
                    .findGeneroByNombreIgnoreCase(nombreDeGenero);

            if(optionalGenero.isPresent()) {

                PeliculaSerie pelicula = mapper.mapToEntity(peliculaRequestDto);
                pelicula.setGenero(optionalGenero.get());
                PeliculaSerie peliculaSave = peliculaRepository.save(pelicula);
                PeliculaSerieResponseDto peliculaResponseDto = mapper.mapToDto(peliculaSave);
                return peliculaResponseDto;

            }else {
                throw new GeneroNotFoundException("El genero '" + nombreDeGenero + "' no existe");
            }

        } else {
            throw new PeliculaNotFoundException("El id de pelicula ingresado numero '" + id + "'  no existe en la base de datos");
        }

    }

}
