package com.besysoft.bootcampspringboot.controlador;

import com.besysoft.bootcampspringboot.DTO.Request.PersonajeRequestDto;
import com.besysoft.bootcampspringboot.services.interfaces.IPersonajeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.*;
import static com.besysoft.bootcampspringboot.utilidades.Validaciones.*;

@Api(value = "Controlador de Personaje", tags = "Acciones permitidas para la entidad de Pelicula/Serie")
@Slf4j
@RestController
@RequestMapping("/personajes")
public class PersonajeControlador {

    @Autowired
    IPersonajeService personajeService;

    @ApiOperation(value = "Permite obtener todos los personajes almacenados")
    @GetMapping
    public ResponseEntity<?> obtenerTodosLosPersonajes() {
            return ok(personajeService.obtenerTodosLosPersonajes());
    }

    @ApiOperation(value = "Permite obtener personajes almacenados por su edad o nombre")
    @GetMapping("/{dato}")
    public ResponseEntity<?> buscarPorEdadONombre(@PathVariable String edadONombre) {
            validarLetrasYNumeros("edad o nombre", edadONombre);
            return ok(personajeService.buscarPorEdadONombre(edadONombre));
    }

    @ApiOperation(value = "Permite obtener personajes almacenados por su rango de edad")
    @GetMapping("/edad")
    public ResponseEntity<?> buscarPorRangoDeEdad (@RequestParam Integer desde, @RequestParam Integer hasta){
        validarEdadPorRango(desde, hasta);
        return ok(personajeService.buscarPersonajePorRangoDeEdad(desde, hasta));
    }

    @ApiOperation(value = "Permite agregar un nuevo personaje")
    @PostMapping
    public ResponseEntity<?> agregarPersonaje(@RequestBody @Valid PersonajeRequestDto personajeRequestDto, BindingResult result){
            return created(personajeService.agregarNuevoPersonaje(personajeRequestDto));
    }

    @ApiOperation(value = "Permite actualizar un personaje existente ingresando su id y los nuevos datos")
    @PutMapping("/{id}")
    public ResponseEntity actualizarPersonajePorId (@PathVariable Long id, @RequestBody @Valid PersonajeRequestDto personajeRequestDto, BindingResult result){
            return ok(personajeService.actualizarPersonajePorId(id, personajeRequestDto));
    }







}
