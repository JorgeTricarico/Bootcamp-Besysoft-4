package com.besysoft.bootcampspringboot.DTO.Mapper;

import com.besysoft.bootcampspringboot.DTO.Response.PeliculaSerieTitleResponseDto;
import com.besysoft.bootcampspringboot.dominio.PeliculaSerie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IPeliculaTitleMapper {

    PeliculaSerieTitleResponseDto mapToDto(PeliculaSerie peliculaSerie);
}
