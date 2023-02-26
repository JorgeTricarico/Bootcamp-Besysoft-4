package com.besysoft.bootcampspringboot.DTO.Mapper;

import com.besysoft.bootcampspringboot.DTO.Response.PersonajeNameResponseDto;
import com.besysoft.bootcampspringboot.dominio.Personaje;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IPersonajeNameResponseMapper {

       PersonajeNameResponseDto mapToDto(Personaje personaje);
}
