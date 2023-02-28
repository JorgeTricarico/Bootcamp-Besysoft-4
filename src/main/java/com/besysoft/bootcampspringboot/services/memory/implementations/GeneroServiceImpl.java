package com.besysoft.bootcampspringboot.services.memory.implementations;

import com.besysoft.bootcampspringboot.DTO.Mapper.IGeneroMapper;
import com.besysoft.bootcampspringboot.DTO.Request.GeneroRequestDto;
import com.besysoft.bootcampspringboot.DTO.Response.GeneroResponseDto;
import com.besysoft.bootcampspringboot.dominio.Genero;
import com.besysoft.bootcampspringboot.respositories.memory.interfaces.IGeneroRepository;
import com.besysoft.bootcampspringboot.services.interfaces.IGeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.badResquest;


@Service
@ConditionalOnProperty(prefix = "app", name = "type-data", havingValue = "memory")
public class GeneroServiceImpl implements IGeneroService {

    @Autowired
    IGeneroRepository generoRepository;
    @Autowired
    IGeneroMapper mapper;

    @Override
    public GeneroRequestDto agregarNuevoGenero(GeneroRequestDto generoRequestDto) {
        Optional<Genero> optinalGenero = generoRepository.obtenerTodosLosGeneros()
                .stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(generoRequestDto.getNombre())).findAny();

        if (optinalGenero.isPresent()) {
            throw new RuntimeException("El nombre de genero '" + generoRequestDto.getNombre() +"' ya existe.");
        }

        Long cantidadDeGeneros = Long.valueOf(generoRepository.obtenerTodosLosGeneros().size());

        generoRepository.agregarNuevoGenero(mapper.mapToEntity(generoRequestDto));

        return generoRequestDto;
    }

    @Override
    public List<GeneroResponseDto> obtenerTodosLosGeneros() {
        List<GeneroResponseDto> listGeneroDto = generoRepository
                .obtenerTodosLosGeneros()
                .stream()
                .map(genero -> mapper
                        .mapToDto(genero))
                .collect(Collectors.toList());
        return listGeneroDto;
    }

    @Override
    public GeneroRequestDto actualizarGeneroPorId(Long id, GeneroRequestDto generoRequestDtoAct) {
        Optional<Genero> optionalGenero = generoRepository.buscarGeneroPorId(id);

        if (optionalGenero.isPresent()) {
            Genero genero = optionalGenero.get();

            if (generoRequestDtoAct.getNombre().isBlank()) {
                throw new IllegalArgumentException("El nombre no puede ser nulo o estar vacio");
            }
            /*if (generoAct.getPeliculasSeries() == null || generoAct.getPeliculaSerie().size()<1){
                return badResquest("Las peliculas sociadas no pueden estar vacias o ser nulas");
            }*/

            genero.setNombre(generoRequestDtoAct.getNombre());
            /*genero.setPeliculaSerie(generoAct.getPeliculaSerie());*/

            return generoRequestDtoAct;
        }else{
            throw new IllegalArgumentException("El id ingresado no existe");
        }
    }
}
