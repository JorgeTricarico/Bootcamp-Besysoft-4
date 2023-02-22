package com.besysoft.bootcampspringboot.controlador;

import com.besysoft.bootcampspringboot.dominio.Personaje;
import com.besysoft.bootcampspringboot.services.interfaces.IPersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.*;
import static com.besysoft.bootcampspringboot.utilidades.Validaciones.*;


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
            return notFound(e.getMessage());
        }catch (RuntimeException e){
            return internalServerError("Error en la conección a la base de datos");
        }
    }
    @GetMapping("/{dato}")
    public ResponseEntity<?> buscarPorEdadONombre(@PathVariable String dato) {
        try {
            validarLetrasYNumeros("edad o nombre", dato);
            return ok(personajeService.buscarPorEdadONombre(dato));
        }catch (IllegalArgumentException e){
            return badResquest(e.getMessage());
        }catch (RuntimeException e){
            return internalServerError(e.getMessage());
        }
    }
    @GetMapping("/edad")
    public ResponseEntity<?> buscarPorRangoDeEdad (@RequestParam Integer desde, @RequestParam Integer hasta){

        try {
            validarEdadPorRango(desde, hasta);
            return ok(personajeService.buscarPersonajePorRangoDeEdad(desde, hasta));
        }catch (IllegalArgumentException e){
            return badResquest(e.getMessage());
        }catch (NullPointerException e){
            return notFound(e.getMessage());
        }catch (RuntimeException e){
            return internalServerError(e.getMessage());
        }
    }
    @PostMapping
    public ResponseEntity<?> agregarPersonaje(@RequestBody Personaje personaje){
        try {
            validarPersonaje(personaje);
            return created(personajeService.agregarNuevoPersonaje(personaje));
        }catch (IllegalArgumentException e){
            return badResquest(e.getMessage());
        }catch (NullPointerException e){
            return notFound(e.getMessage());
        }catch (RuntimeException e){
            return internalServerError(e.getMessage());
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity actualizarPersonajePorId (@PathVariable Long id, @RequestBody Personaje personaje){
        try {
            validarPersonaje(personaje);
            return ok(personajeService.actualizarPersonajePorId(id, personaje));
        }catch (IllegalArgumentException e){
            return badResquest(e.getMessage());
        }catch (NullPointerException e){
            return notFound(e.getMessage());
        }catch (RuntimeException e){
            return internalServerError(e.getMessage());
        }

    }







}
