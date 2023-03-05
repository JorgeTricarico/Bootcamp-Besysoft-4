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
            return ok(peliculaService.obtenerTodasLasPeliculas());
    }

    @GetMapping("/{tituloOGenero}")
    public ResponseEntity<?> buscarPorTituloOGenero(@PathVariable String tituloOGenero) {
            validarTitulo("pelicula, serie o Genero", tituloOGenero);
            List<PeliculaSerieResponseDto> peliculaSeries = peliculaService.buscarPeliculaPorTituloOGenero(tituloOGenero);
            return ok(peliculaSeries);
    }

    @GetMapping("/fechas")
    public ResponseEntity<?> buscarPorFecha(@RequestParam String desde, @RequestParam String hasta) {
            validarFecha(desde, hasta);
            return ok(peliculaService.buscarPeliculaPorFecha(desde, hasta));
    }

    @GetMapping("/calificacion")
    public ResponseEntity<?> buscarPorCalificacion(@RequestParam Integer desde, @RequestParam Integer hasta){
            validarCalificacionPorRango(desde, hasta);
            return ok(peliculaService.buscarPeliculasPorCalificacion(desde, hasta));


    }

    @PostMapping
    public ResponseEntity<?> agregarPelicula(@RequestBody @Valid PeliculaSerieRequestDto peliculaRequestDto, BindingResult result){
                return errorInValidator(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPelicula (@PathVariable Long id, @RequestBody @Valid PeliculaSerieRequestDto peliculaRequestDto, BindingResult result){
                return errorInValidator(result);
    }




}