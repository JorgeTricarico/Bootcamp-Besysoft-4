package com.besysoft.bootcampspringboot.controlador;

import com.besysoft.bootcampspringboot.DTO.Request.GeneroRequestDto;
import com.besysoft.bootcampspringboot.DTO.Response.GeneroResponseDto;
import com.besysoft.bootcampspringboot.services.interfaces.IGeneroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.*;
import static com.besysoft.bootcampspringboot.utilidades.Validaciones.logUnexpected;
import static com.besysoft.bootcampspringboot.utilidades.Validaciones.logValidation;

@Slf4j
@RestController
@RequestMapping("/generos")
public class GeneroControlador {

    @Autowired
    IGeneroService generoService;

    @GetMapping
    public ResponseEntity<?> obtenerTodosLosGeneros(){
            List<GeneroResponseDto> generosDto = generoService.obtenerTodosLosGeneros();
            return ok(generosDto);
    }
    @PostMapping
    public ResponseEntity<?> agregarNuevoGenero(@RequestBody @Valid GeneroRequestDto generoDto, BindingResult result) {
            return created(generoService.agregarNuevoGenero(generoDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarGeneroPorId (@PathVariable Long id, @RequestBody @Valid GeneroRequestDto generorequestDto, BindingResult result){
            return created(generoService.actualizarGeneroPorId(id, generorequestDto));
    }

}
