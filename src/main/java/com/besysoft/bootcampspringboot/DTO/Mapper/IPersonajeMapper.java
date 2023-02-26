package com.besysoft.bootcampspringboot.DTO.Mapper;

import com.besysoft.bootcampspringboot.DTO.Request.PersonajeRequestDto;
import com.besysoft.bootcampspringboot.DTO.Response.PersonajeResponseDto;
import com.besysoft.bootcampspringboot.dominio.Personaje;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import javax.validation.constraints.Min;

@Mapper(componentModel = "spring")
public interface IPersonajeMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "peliculasSeries", ignore = true)
    })
    Personaje mapToEntity(PersonajeRequestDto dto);

    PersonajeResponseDto mapToDto(Personaje personaje);
}
