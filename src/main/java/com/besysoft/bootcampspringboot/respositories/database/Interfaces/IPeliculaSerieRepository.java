package com.besysoft.bootcampspringboot.respositories.database.Interfaces;

import com.besysoft.bootcampspringboot.dominio.PeliculaSerie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IPeliculaSerieRepository extends JpaRepository<PeliculaSerie,Long> {
    Optional<PeliculaSerie> findByTituloIgnoreCase(String titulo);
    List<PeliculaSerie> findAllByFechaDeCreacionBetween(LocalDate desde, LocalDate hasta);
    List<PeliculaSerie> findAllByCalificacionBetween(Integer start, Integer end);
    Boolean existsByTitulo(String titulo);
}
