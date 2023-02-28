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
        try {
            return ok(personajeService.obtenerTodosLosPersonajes());
        }catch (NullPointerException e){
            logValidation(e);
            return notFound(e.getMessage());
        }catch (RuntimeException e){
            logUnexpected(e);
            return internalServerError("Error en la conección a la base de datos");
        }
    }
    @GetMapping("/{dato}")
    public ResponseEntity<?> buscarPorEdadONombre(@PathVariable String edadONombre) {
        try {
            validarLetrasYNumeros("edad o nombre", edadONombre);
            return ok(personajeService.buscarPorEdadONombre(edadONombre));
        }catch (IllegalArgumentException e){
            logValidation(e);
            return badResquest(e.getMessage());
        }catch (RuntimeException e){
            logUnexpected(e);
            return internalServerError(e.getMessage());
        }
    }
    @GetMapping("/edad")
    public ResponseEntity<?> buscarPorRangoDeEdad (@RequestParam Integer desde, @RequestParam Integer hasta){

        try {
            validarEdadPorRango(desde, hasta);
            return ok(personajeService.buscarPersonajePorRangoDeEdad(desde, hasta));
        }catch (IllegalArgumentException e){
            logValidation(e);
            return badResquest(e.getMessage());
        }catch (NullPointerException e){
            logValidation(e);
            return notFound(e.getMessage());
        }catch (RuntimeException e){
            logUnexpected(e);
            return internalServerError(e.getMessage());
        }
    }
    @PostMapping
    public ResponseEntity<?> agregarPersonaje(@RequestBody @Valid PersonajeRequestDto personajeRequestDto, BindingResult result){
        try {
            if(result.hasErrors()){
                return errorInValidator(result);
            }
            return created(personajeService.agregarNuevoPersonaje(personajeRequestDto));
        }catch (IllegalArgumentException e){
            logValidation(e);
            return badResquest(e.getMessage());
        }catch (NullPointerException e){
            logValidation(e);
            return notFound(e.getMessage());
        }catch (RuntimeException e){
            logUnexpected(e);
            return internalServerError(e.getMessage());
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity actualizarPersonajePorId (@PathVariable Long id, @RequestBody @Valid PersonajeRequestDto personajeRequestDto, BindingResult result){
        try {
            if(result.hasErrors()){
                return errorInValidator(result);
            }
            return ok(personajeService.actualizarPersonajePorId(id, personajeRequestDto));
        }catch (IllegalArgumentException e){
            logValidation(e);
            return badResquest(e.getMessage());
        }catch (NullPointerException e){
            logValidation(e);
            return notFound(e.getMessage());
        }catch (RuntimeException e){
            logUnexpected(e);
            return internalServerError(e.getMessage());
        }
    }







}
