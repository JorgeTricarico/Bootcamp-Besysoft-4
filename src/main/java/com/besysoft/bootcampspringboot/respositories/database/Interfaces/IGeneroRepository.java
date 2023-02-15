package com.besysoft.bootcampspringboot.respositories.database.Interfaces;

import com.besysoft.bootcampspringboot.dominio.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IGeneroRepository extends JpaRepository<Genero, Long> {

    Boolean existsGeneroById(Long id);

    Optional<Genero> findGeneroByNombreIgnoreCase(String nombre);
    Boolean existsGeneroByNombreIgnoreCase(String nombre);


}
