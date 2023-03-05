package com.besysoft.bootcampspringboot.controlador.handler;

import com.besysoft.bootcampspringboot.DTO.Response.CustomError;
import com.besysoft.bootcampspringboot.DTO.Response.ErrorDetail;
import com.besysoft.bootcampspringboot.DTO.Response.ExceptionDTO;
import com.besysoft.bootcampspringboot.DTO.Response.ValidExceptionResponse;
import com.besysoft.bootcampspringboot.Exception.BadRequestExcepion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.besysoft.bootcampspringboot.utilidades.Validaciones.logUnexpected;
import static com.besysoft.bootcampspringboot.utilidades.Validaciones.logValidation;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class ApiControllerAdvice {

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidExceptionResponse handleValidationException(MethodArgumentNotValidException ex) {
        logValidation(ex);
        List<FieldError> errores = ex.getBindingResult().getFieldErrors();
        Map<String, String> detalle = new HashMap<>();
        errores.forEach(error -> {
            detalle.put(error.getField(), error.getDefaultMessage());
        });
//        LocalDateTime timestamp = LocalDateTime.now();
//        return new ValidExceptionResponse(timestamp, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(),obtenerPath() ,"Validaciones", detalle);
        return new ValidExceptionResponse("validaciones", detalle);
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomError handleBadResquestException(BadRequestExcepion ex){

        return new CustomError(ex.getMessage());
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomError handleNotFound(BadRequestExcepion ex){
        return new CustomError(ex.getMessage());
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomError handleServerInternalErrorException(RuntimeException ex){
        logUnexpected(ex);
        return new CustomError(ex.getMessage());
    }


    /*public ExceptionDTO manejarExcepcion(Exception ex) {
        // Obtener información de la excepción
        String mensaje = ex.getMessage();
        LocalDateTime timestamp = LocalDateTime.now();

        // Crear objeto ErrorDetail con información adicional de la excepción
        ErrorDetail errorDetail = new ErrorDetail("global", "Se ha producido un error en el servidor");

        // Crear objeto ErrorResponse y añadir el objeto ErrorDetail
        ExceptionDTO errorResponse = new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), mensaje, timestamp,errorDetail, getStackTrace(ex));


        return errorResponse;
    }*/

    private String getStackTrace (Throwable ex){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }

    /*private String obtenerPath() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getRequest().getRequestURI();
        }
        return null;
    }*/

}
