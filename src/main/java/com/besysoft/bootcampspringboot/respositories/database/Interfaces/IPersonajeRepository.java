package com.besysoft.bootcampspringboot.respositories.database.Interfaces;
import com.besysoft.bootcampspringboot.dominio.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPersonajeRepository extends JpaRepository<Personaje, Long> {

    List<Personaje> findByNombreIgnoreCase(String nombre);
    List<Personaje> findByEdad(Integer edad);
    List<Personaje> findByEdadBetween(Integer start, Integer end);


}
