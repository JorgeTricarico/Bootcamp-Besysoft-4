package com.besysoft.bootcampspringboot.controlador;

import com.besysoft.bootcampspringboot.DTO.Request.GeneroRequestDto;
import com.besysoft.bootcampspringboot.DTO.Response.GeneroResponseDto;
import com.besysoft.bootcampspringboot.services.interfaces.IGeneroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.created;
import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.ok;

@Api(value = "Controlador de Genero", tags = "Acciones permitidas para la entidad Genero")
@Slf4j
@RestController
@RequestMapping("/generos")
public class GeneroControlador {

    @Autowired
    IGeneroService generoService;

    @ApiOperation(value = "Permite obtener todos los generos almacenados")
    @GetMapping
    public ResponseEntity<?> obtenerTodosLosGeneros(){
            List<GeneroResponseDto> generosDto = generoService.obtenerTodosLosGeneros();
            return ok(generosDto);
    }

    @ApiOperation(value = "Permite agregar un nuevo genero")
    @PostMapping
    public ResponseEntity<?> agregarNuevoGenero(@RequestBody @Valid GeneroRequestDto generoDto) {
            return created(generoService.agregarNuevoGenero(generoDto));
    }


    @ApiOperation(value = "Permite actualizar un genero existente ingresando su id y los nuevos datos")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarGeneroPorId (@PathVariable Long id, @RequestBody @Valid GeneroRequestDto generorequestDto){
            return created(generoService.actualizarGeneroPorId(id, generorequestDto));
    }

}
