package com.besysoft.bootcampspringboot.services.database.implementations;

import com.besysoft.bootcampspringboot.DTO.Mapper.IGeneroMapper;
import com.besysoft.bootcampspringboot.DTO.Request.GeneroRequestDto;
import com.besysoft.bootcampspringboot.DTO.Response.GeneroResponseDto;
import com.besysoft.bootcampspringboot.dominio.Genero;
import com.besysoft.bootcampspringboot.respositories.database.Interfaces.IGeneroRepository;
import com.besysoft.bootcampspringboot.services.interfaces.IGeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ConditionalOnProperty(prefix = "app", name = "type-data", havingValue = "database")
@Service
public class GeneroServiceImpl implements IGeneroService {


    public GeneroServiceImpl(IGeneroRepository generoRepository, IGeneroMapper mapper) {
        this.generoRepository = generoRepository;
        this.mapper = mapper;
    }


    private final IGeneroRepository generoRepository;

    private final IGeneroMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<GeneroResponseDto> obtenerTodosLosGeneros() {
        try {
            List<Genero> listaDeGeneros = generoRepository.findAll();
            if (listaDeGeneros.isEmpty()){
                throw new NullPointerException("No existen generos en la base de datos");
            }
            List<GeneroResponseDto> listaGeneroDto = listaDeGeneros.stream().map(genero -> mapper.mapToDto(genero)).collect(Collectors.toList());
            return listaGeneroDto;
        }catch (RuntimeException e){
            throw new RuntimeException("Error en la conexion a la base de datos");
        }

    }

    @Transactional(readOnly = false)
    public GeneroResponseDto agregarNuevoGenero(GeneroRequestDto generoRequestDto) {

        Boolean existeGenero = generoRepository.existsGeneroByNombreIgnoreCase(generoRequestDto.getNombre());

        if (existeGenero) {
            throw new IllegalArgumentException("El nombre de genero '" + generoRequestDto.getNombre() +"' ya existe.");
        }
        Genero generoSave = generoRepository.save(mapper.mapToEntity(generoRequestDto));

        GeneroResponseDto generoResponseDto = mapper.mapToDto(generoSave);

        return generoResponseDto;//new ResponseEntity<>(genero, HttpStatus.CREATED);
    }

    @Override
    @Transactional(readOnly = false)
    public GeneroResponseDto actualizarGeneroPorId(Long id, GeneroRequestDto generoRequestDtoAct) {

        Boolean existeGenero = generoRepository.existsGeneroById(id);

        if (existeGenero) {
            if (generoRepository.existsGeneroByNombreIgnoreCase(generoRequestDtoAct.getNombre())){
                throw new IllegalArgumentException("El nombre de genero '"+generoRequestDtoAct.getNombre()+"' ya existe.");
            }
            Optional<Genero> generoById = generoRepository.findById(id);
            Genero genero  = generoById.orElseThrow();
            genero.setNombre(generoRequestDtoAct.getNombre());
            if (genero.getId() != null){
                genero.setId(genero.getId());
            }

            GeneroResponseDto generoResponseDto = mapper.mapToDto(genero);
            return generoResponseDto;
        }else{
            throw new NullPointerException("No exise ningun genero en la base de datos.");
        }
    }
}
