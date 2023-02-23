package com.besysoft.bootcampspringboot.respositories.memory.implementations;

import com.besysoft.bootcampspringboot.dominio.Genero;
import com.besysoft.bootcampspringboot.dominio.PeliculaSerie;
import com.besysoft.bootcampspringboot.respositories.memory.interfaces.IGeneroRepository;
import com.besysoft.bootcampspringboot.respositories.memory.interfaces.IPeliculaSerieRepository;
import com.besysoft.bootcampspringboot.utilidades.DatoDummyn;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

@Repository
public class PeliculaSerieRepositoryImpl implements IPeliculaSerieRepository {


    List<PeliculaSerie> listaDePeliculas = IPeliculaSerieRepository.crearPeliculaSerie();

    public PeliculaSerieRepositoryImpl() throws DataFormatException {
    }


    @Override
    public List<PeliculaSerie> obtenerTodasLasPeliculas() {
        return listaDePeliculas;
    }

    @Override
    public List<PeliculaSerie> buscarPeliculaPorGenero (String nombreDeGenero){


        List<PeliculaSerie> oPeliculas = listaDePeliculas
                .stream()
                .filter(peliculaSerie -> peliculaSerie.getGenero().getNombre().equalsIgnoreCase(nombreDeGenero))
                .collect(Collectors.toList());

        if (oPeliculas.size()>0){
            return  oPeliculas;
        }
        return null;
    }
    @Override
    public List<PeliculaSerie> buscarPeliculasPorCalificaciones(Integer desde, Integer hasta) {
        return listaDePeliculas.stream()
                .filter(ps -> ps.getCalificacion() >= desde && ps.getCalificacion()<= hasta)
                .collect(Collectors.toList());
    }

    @Override
    public List<PeliculaSerie> buscarComoRepoPeliculaPorFecha(LocalDate fechaInicio, LocalDate fechaFinal) {
        return listaDePeliculas.stream()
                .filter(ps -> ps.getFechaDeCreacion().isAfter(fechaInicio.minusDays(1))
                        && ps.getFechaDeCreacion().isBefore(fechaFinal.plusDays(1)))
                .collect(Collectors.toList());
    }

    @Override
    public void agregarNuevaPelicula(PeliculaSerie peliculaSerie) {
        listaDePeliculas.add(peliculaSerie);
    }

    @Override
    public Optional<PeliculaSerie> buscarTituloDePelicula(String titulo) {
        return listaDePeliculas.stream()
                .filter(pelis -> pelis.getTitulo().equalsIgnoreCase(titulo))
                .findAny();
    }

    @Override
    public Optional<PeliculaSerie> buscarPeliculaPorId(Long id) {
        return listaDePeliculas.stream()
                .filter(p -> p.getId().equals(id))
                .findAny();
    }

}
