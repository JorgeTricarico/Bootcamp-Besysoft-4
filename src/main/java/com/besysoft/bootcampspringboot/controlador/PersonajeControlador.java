package com.besysoft.bootcampspringboot.controlador;

import com.besysoft.bootcampspringboot.DTO.Request.PersonajeRequestDto;
import com.besysoft.bootcampspringboot.services.interfaces.IPersonajeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.*;
import static com.besysoft.bootcampspringboot.utilidades.Validaciones.*;

@Slf4j
@RestController
@RequestMapping("/personajes")
public class PersonajeControlador {

    @Autowired
    IPersonajeService personajeService;

    @GetMapping
    public ResponseEntity<?> obtenerTodosLosPersonajes() {
            return ok(personajeService.obtenerTodosLosPersonajes());
    }
    @GetMapping("/{dato}")
    public ResponseEntity<?> buscarPorEdadONombre(@PathVariable String edadONombre) {
            validarLetrasYNumeros("edad o nombre", edadONombre);
            return ok(personajeService.buscarPorEdadONombre(edadONombre));
    }
    @GetMapping("/edad")
    public ResponseEntity<?> buscarPorRangoDeEdad (@RequestParam Integer desde, @RequestParam Integer hasta){
        validarEdadPorRango(desde, hasta);
        return ok(personajeService.buscarPersonajePorRangoDeEdad(desde, hasta));
    }
    @PostMapping
    public ResponseEntity<?> agregarPersonaje(@RequestBody @Valid PersonajeRequestDto personajeRequestDto, BindingResult result){
            return created(personajeService.agregarNuevoPersonaje(personajeRequestDto));
    }
    @PutMapping("/{id}")
    public ResponseEntity actualizarPersonajePorId (@PathVariable Long id, @RequestBody @Valid PersonajeRequestDto personajeRequestDto, BindingResult result){
            return ok(personajeService.actualizarPersonajePorId(id, personajeRequestDto));
    }







}
