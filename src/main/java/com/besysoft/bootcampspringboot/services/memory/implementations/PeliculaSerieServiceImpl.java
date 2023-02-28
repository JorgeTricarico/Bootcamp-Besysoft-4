package com.besysoft.bootcampspringboot.services.memory.implementations;

import com.besysoft.bootcampspringboot.DTO.Mapper.IPeliculaSerieMapper;
import com.besysoft.bootcampspringboot.DTO.Request.PeliculaSerieRequestDto;
import com.besysoft.bootcampspringboot.DTO.Response.PeliculaSerieResponseDto;
import com.besysoft.bootcampspringboot.dominio.PeliculaSerie;
import com.besysoft.bootcampspringboot.respositories.memory.interfaces.IPeliculaSerieRepository;
import com.besysoft.bootcampspringboot.services.interfaces.IPeliculaSerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

import static com.besysoft.bootcampspringboot.utilidades.Fechas.formatear;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-data", havingValue = "memory")

public class PeliculaSerieServiceImpl implements IPeliculaSerieService {

    @Autowired
    IPeliculaSerieRepository peliculaSerieRepository;
    @Autowired
    IPeliculaSerieMapper mapper;

    @Override
    public List<PeliculaSerieResponseDto> obtenerTodasLasPeliculas() {
        List<PeliculaSerie> peliculas = peliculaSerieRepository.obtenerTodasLasPeliculas();
        List<PeliculaSerieResponseDto> peliculaSerieResponseDtos= peliculas.stream()
                .map(pelicula -> mapper
                        .mapToDto(pelicula))
                .collect(Collectors.toList());
        return peliculaSerieResponseDtos;
    }

    @Override
    public List<PeliculaSerieResponseDto> buscarPeliculaPorTituloOGenero(String tituloOGenero) {
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
                List<PeliculaSerieResponseDto> peliculaSerieResponseDtos= peliculasPorGenero.stream()
                        .map(pelicula -> mapper
                                .mapToDto(pelicula))
                        .collect(Collectors.toList());
                return peliculaSerieResponseDtos;
            }else {
                throw new NullPointerException("No existe pelicula o genero con el nombre '"+tituloOGenero+"'");
            }
        }
        List<PeliculaSerie> listaPelicula = new ArrayList<>();
        listaPelicula.add(OPeliculasSeries.get());
        List<PeliculaSerieResponseDto> peliculaSerieResponseDtos= listaPelicula.stream()
                .map(pelicula -> mapper
                        .mapToDto(pelicula))
                .collect(Collectors.toList());

        return peliculaSerieResponseDtos;


    }

    @Override
    public List<PeliculaSerieResponseDto> buscarPeliculaPorFecha(String desde, String hasta) throws DataFormatException {
        LocalDate fechaInicio = formatear(desde);
        LocalDate fechaFinal = formatear(hasta);
        if (desde.compareTo(hasta) > 0) {
            throw new DataFormatException("Rango de fecha inválido.");
        }
        List<PeliculaSerie> peliculas = peliculaSerieRepository.buscarComoRepoPeliculaPorFecha(fechaInicio,fechaFinal);

        if (peliculas.isEmpty()){
            throw new NullPointerException("No se encontro peliculas con las fechas ingresadas");
        }

        List<PeliculaSerieResponseDto> peliculaSerieResponseDtos= peliculas.stream()
                .map(pelicula -> mapper
                        .mapToDto(pelicula))
                .collect(Collectors.toList());
        return peliculaSerieResponseDtos;
    }

    @Override
    public List<PeliculaSerieResponseDto> buscarPeliculasPorCalificacion(Integer desde, Integer hasta) {
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
        List<PeliculaSerieResponseDto> peliculaSerieResponseDtos= peliculas.stream()
                .map(pelicula -> mapper
                        .mapToDto(pelicula))
                .collect(Collectors.toList());
        return peliculaSerieResponseDtos;
    }

    @Override
    public PeliculaSerieResponseDto agregarNuevaPelicula(PeliculaSerieRequestDto peliculaSerieRequestDto) throws DataFormatException {

        Optional<PeliculaSerie> optionalPelicula = peliculaSerieRepository.obtenerTodasLasPeliculas()
                .stream().filter(p -> p.getTitulo().equalsIgnoreCase(peliculaSerieRequestDto.getTitulo()))
                .findAny();

        if (optionalPelicula.isPresent()) {
            throw new IllegalArgumentException("El nombre de pelicula o serie ingresado ya existe");
        }


        Long cantidadDePeliculas = Long.valueOf(peliculaSerieRepository.obtenerTodasLasPeliculas().size());
        PeliculaSerie pelicula = mapper.mapToEntity(peliculaSerieRequestDto);
        pelicula.setId(cantidadDePeliculas + 1);
        peliculaSerieRepository.agregarNuevaPelicula(pelicula);

        PeliculaSerieResponseDto peliculaSerieResponseDto = mapper.mapToDto(pelicula);
        return peliculaSerieResponseDto;
    }

    @Override
    public PeliculaSerieResponseDto actualizarPeliculaPorId(Long id, PeliculaSerieRequestDto peliculaSerieRequestDto) throws DataFormatException {
        Optional<PeliculaSerie> optionalPelicula = peliculaSerieRepository.buscarPeliculaPorId(id);

        if(optionalPelicula.isPresent()){

            PeliculaSerie pelicula = mapper.mapToEntity(peliculaSerieRequestDto);
            pelicula.setId(id);
            peliculaSerieRepository.agregarNuevaPelicula(pelicula);
            return mapper.mapToDto(pelicula);
        } else {
            throw new NullPointerException("El id de pelicula o serie numero "+id+" no existe");
        }
    }


}
