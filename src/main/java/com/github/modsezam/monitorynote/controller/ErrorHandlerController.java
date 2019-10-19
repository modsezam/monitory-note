package com.github.modsezam.monitorynote.controller;

import com.github.modsezam.monitorynote.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ErrorHandlerController {
    @ExceptionHandler (value = {EntityNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleEntityNotFoundException (EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Unable to find entity: " + exception.getMessage()));

    }
}
