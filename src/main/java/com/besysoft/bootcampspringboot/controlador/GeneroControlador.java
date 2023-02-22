package com.besysoft.bootcampspringboot.controlador;

import com.besysoft.bootcampspringboot.dominio.Genero;
import com.besysoft.bootcampspringboot.services.interfaces.IGeneroService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.*;
import static com.besysoft.bootcampspringboot.utilidades.Validaciones.*;


@RestController
@RequestMapping("/generos")
public class GeneroControlador {

    @Autowired
    IGeneroService generoService;

    @GetMapping
    public ResponseEntity<?> obtenerTodosLosGeneros(){
        try {
            List<Genero> generos = generoService.obtenerTodosLosGeneros();
            return ok(generos);
        } catch (NullPointerException e) {
            return notFound(e.getMessage());
        }catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping
    public ResponseEntity<?> agregarNuevoGenero(@RequestBody Genero genero ) {
        try{
            validarNombre("genero", genero.getNombre());
            return created(generoService.agregarNuevoGenero(genero));
        } catch (IllegalArgumentException e) {
            return badResquest(e.getMessage());
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarGeneroPorId (@PathVariable Long id, @RequestBody Genero genero){
        try{
            validarNombre("genero", genero.getNombre());
            return created(generoService.actualizarGeneroPorId(id, genero));
        } catch (IllegalArgumentException e) {
            return badResquest(e.getMessage());
        } catch (NullPointerException e){
            return notFound(e.getMessage());
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
