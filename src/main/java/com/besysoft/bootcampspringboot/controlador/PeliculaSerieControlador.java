package com.besysoft.bootcampspringboot.controlador;

import com.besysoft.bootcampspringboot.DTO.Request.PeliculaSerieRequestDto;
import com.besysoft.bootcampspringboot.DTO.Response.PeliculaSerieResponseDto;
import com.besysoft.bootcampspringboot.services.interfaces.IPeliculaSerieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.zip.DataFormatException;

import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.badResquest;
import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.created;
import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.errorInValidator;
import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.internalServerError;
import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.notFound;
import static com.besysoft.bootcampspringboot.utilidades.ResponseHttp.ok;
import static com.besysoft.bootcampspringboot.utilidades.Validaciones.logUnexpected;
import static com.besysoft.bootcampspringboot.utilidades.Validaciones.logValidation;
import static com.besysoft.bootcampspringboot.utilidades.Validaciones.validarCalificacionPorRango;
import static com.besysoft.bootcampspringboot.utilidades.Validaciones.validarFecha;
import static com.besysoft.bootcampspringboot.utilidades.Validaciones.validarTitulo;

@Slf4j
@RestController
@RequestMapping("/peliculas")
public class PeliculaSerieControlador {

    @Autowired
    IPeliculaSerieService peliculaService;

    @GetMapping
    public ResponseEntity<?> mostrarTodasLasPeliculas() {
        try {
            return ok(peliculaService.obtenerTodasLasPeliculas());
        }catch (NullPointerException e){
            return notFound(e.getMessage());
        }catch (RuntimeException e){
            return internalServerError(e.getMessage());
        }
    }

    @GetMapping("/{tituloOGenero}")
    public ResponseEntity<?> buscarPorTituloOGenero(@PathVariable String tituloOGenero) {
        try {
            validarTitulo("pelicula, serie o Genero", tituloOGenero);
            List<PeliculaSerieResponseDto> peliculaSeries = peliculaService.buscarPeliculaPorTituloOGenero(tituloOGenero);
            return ok(peliculaSeries);
        }catch (IllegalArgumentException e){
            logValidation(e);
            return badResquest(e.getMessage());
        }catch (NullPointerException e){
            logValidation(e);
            return notFound(e.getMessage());
        }catch (RuntimeException e){
            logValidation(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/fechas")
    public ResponseEntity<?> buscarPorFecha(@RequestParam String desde, @RequestParam String hasta) {
        try {
            validarFecha(desde, hasta);
            return ok(peliculaService.buscarPeliculaPorFecha(desde, hasta));
        }catch (IllegalArgumentException | DataFormatException e){
            logValidation(e);
            return badResquest(e.getMessage());
        }catch (NullPointerException e){
            logValidation(e);
            return notFound(e.getMessage());
        } catch (RuntimeException e){
            logValidation(e);
            return internalServerError(e.getMessage());
        }
    }

    @GetMapping("/calificacion")
    public ResponseEntity<?> buscarPorCalificacion(@RequestParam Integer desde, @RequestParam Integer hasta){
        try {
            validarCalificacionPorRango(desde, hasta);
            return ok(peliculaService.buscarPeliculasPorCalificacion(desde, hasta));
        } catch (IllegalArgumentException e) {
            logValidation(e);
            return badResquest(e.getMessage());
        }catch (NullPointerException e) {
            logValidation(e);
            return notFound(e.getMessage());
        }catch (RuntimeException e){
            logValidation(e);
            return internalServerError(e.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity<?> agregarPelicula(@RequestBody @Valid PeliculaSerieRequestDto peliculaRequestDto, BindingResult result){
        try {
            if(result.hasErrors()){
                return errorInValidator(result);
            }
            return created(peliculaService.agregarNuevaPelicula(peliculaRequestDto));
        }catch (IllegalArgumentException e){
            logValidation(e);
            return badResquest(e.getMessage());
        } catch (RuntimeException e){
            logUnexpected(e);
            return internalServerError(e.toString());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPelicula (@PathVariable Long id, @RequestBody @Valid PeliculaSerieRequestDto peliculaRequestDto, BindingResult result){
        try {
            if(result.hasErrors()){
                return errorInValidator(result);
            }
            return created(peliculaService.actualizarPeliculaPorId(id, peliculaRequestDto));
        }catch (NullPointerException e){
            logValidation(e);
            return notFound(e.getMessage());
        }catch (IllegalArgumentException e){
            logValidation(e);
            return badResquest(e.getMessage());
        } catch (RuntimeException e){
            logUnexpected(e);
            return internalServerError(e.getMessage());
        }
    }




}