package com.Clinica.clinica;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

// Manejo de errores
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = Logger.getLogger(String.valueOf(GlobalExceptionHandler.class));
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> manejoDeErrores(Exception ex, WebRequest req){

        logger.error(Exception.class);
        return new ResponseEntity("Error" + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
