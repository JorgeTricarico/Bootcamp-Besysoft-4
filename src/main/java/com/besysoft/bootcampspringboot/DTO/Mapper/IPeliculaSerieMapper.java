package com.besysoft.bootcampspringboot.DTO.Mapper;

import com.besysoft.bootcampspringboot.DTO.Request.PeliculaSerieRequestDto;
import com.besysoft.bootcampspringboot.DTO.Response.PeliculaSerieResponseDto;
import com.besysoft.bootcampspringboot.DTO.Response.PersonajeNameResponseDto;
import com.besysoft.bootcampspringboot.dominio.PeliculaSerie;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {IPersonajeNameResponseMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IPeliculaSerieMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
//            @Mapping(target = "genero.nombre", source = "nombreGenero"),
            @Mapping(target = "genero.nombre", ignore = true),
            @Mapping(target = "personajesAsociados", ignore = true),
            @Mapping(target = "fechaDeCreacion", dateFormat = "dd-MM-yyyy")
    })
    PeliculaSerie mapToEntity(PeliculaSerieRequestDto dto);

    @Mappings({
            @Mapping(target = "nombreGenero", source = "genero.nombre"),
            @Mapping(target = "fechaDeCreacion", dateFormat = "dd-MM-yyyy")
    })
    PeliculaSerieResponseDto mapToDto(PeliculaSerie peliculaSerie);
}
