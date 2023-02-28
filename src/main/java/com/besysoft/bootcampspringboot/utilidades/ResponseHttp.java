package com.besysoft.bootcampspringboot.utilidades;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ResponseHttp {


    public static ResponseEntity<?> ok(Object object) {
        Map<String, Object> mensajeBody = new HashMap<>();
        mensajeBody.put("success", Boolean.TRUE);
        mensajeBody.put("object", object);
        return ResponseEntity.ok().body(mensajeBody);
    }
    public static ResponseEntity<?> created(Object object) {
        Map<String, Object> mensajeBody = new HashMap<>();
        mensajeBody.put("success", Boolean.TRUE);
        mensajeBody.put("object", object);
        return new ResponseEntity<>(mensajeBody, HttpStatus.CREATED);
    }
    public static ResponseEntity<?> badResquest(String mensaje, Object argumentoFormat) {
        Map<String, Object> mensajeBody = new HashMap<>();
        mensajeBody.put("success", Boolean.FALSE);
        mensajeBody.put("mensaje", String.format(mensaje, argumentoFormat));
        return ResponseEntity.badRequest().body(mensajeBody);
    }

    public static ResponseEntity<?> badResquest(String mensaje) {
        Map<String, Object> mensajeBody = new HashMap<>();
        mensajeBody.put("success", Boolean.FALSE);
        mensajeBody.put("mensaje", mensaje);
        return ResponseEntity.badRequest().body(mensajeBody);
    }

    public static ResponseEntity<?> notFound(String mensaje) {
        Map<String, Object> mensajeBody = new HashMap<>();
        mensajeBody.put("success", Boolean.FALSE);
        mensajeBody.put("mensaje", mensaje);
        return new ResponseEntity<>(mensajeBody, HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<?> notFound(String mensaje, Object argumentoFormat) {
        Map<String, Object> mensajeBody = new HashMap<>();
        mensajeBody.put("success", Boolean.FALSE);
        mensajeBody.put("mensaje", String.format(mensaje, argumentoFormat));
        return new ResponseEntity<>(mensajeBody, HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<?> notFound(Object object) {
        Map<String, Object> mensajeBody = new HashMap<>();
        mensajeBody.put("success", Boolean.TRUE);
        mensajeBody.put("object", object);
        return new ResponseEntity<>(mensajeBody, HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<?> internalServerError(String mensaje) {
        Map<String, Object> mensajeBody = new HashMap<>();
        mensajeBody.put("success", Boolean.FALSE);
        mensajeBody.put("mensaje", mensaje);
        return new ResponseEntity<>(mensajeBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    public static HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("app-info", "contacto@bootcamp.com");
        return headers;
    }

    public static ResponseEntity<?> errorInValidator(BindingResult result){

            Map<String, String> validaciones = new HashMap<>();
        List<FieldError> fieldErrors = result.getFieldErrors();
        fieldErrors.forEach(error -> {
                log.info("Se lanzo una validacion @Valid: \n Atributo: " + error.getField() + " - Validacion: " + error.getDefaultMessage());
                validaciones.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(validaciones);
    }
}
