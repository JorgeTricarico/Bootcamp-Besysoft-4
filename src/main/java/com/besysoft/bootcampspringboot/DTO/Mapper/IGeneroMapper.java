package com.besysoft.bootcampspringboot.DTO.Mapper;

import com.besysoft.bootcampspringboot.DTO.Request.GeneroRequestDto;
import com.besysoft.bootcampspringboot.DTO.Response.GeneroResponseDto;
import com.besysoft.bootcampspringboot.dominio.Genero;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface IGeneroMapper {


    @Mapping(target = "id", ignore = true)
    Genero mapToEntity(GeneroRequestDto dto);

    GeneroResponseDto mapToDto(Genero genero);
}
