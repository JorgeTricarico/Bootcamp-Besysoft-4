package com.besysoft.bootcampspringboot.utilidades;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

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
        return ResponseEntity.internalServerError().headers(headers()).body(mensajeBody);
    }



    public static HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("app-info", "contacto@bootcamp.com");
        return headers;
    }
}
