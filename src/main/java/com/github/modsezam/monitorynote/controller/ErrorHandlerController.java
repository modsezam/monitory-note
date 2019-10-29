package com.github.modsezam.monitorynote.controller;

import com.github.modsezam.monitorynote.model.ErrorMessage;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorHandlerController implements ErrorController {

//    @ExceptionHandler (value = {EntityNotFoundException.class})
//    public ResponseEntity<ErrorMessage> handleEntityNotFoundException (EntityNotFoundException exception) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Unable to find entity: " + exception.getMessage()));
//    }

    @ExceptionHandler (value = {EntityNotFoundException.class})
    public String handleEntityNotFoundException (Model model, EntityNotFoundException exception) {
        model.addAttribute("errorMessage", "Unable to find entity: " + exception.getMessage());
        return "error-page";
    }

    @RequestMapping("/error")
    public String handleError(Model model, HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errorMessage", "sorry, this is 404 status");
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("errorMessage", "sorry, this is 500 status");
            }
        }
        return "error-page";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}
