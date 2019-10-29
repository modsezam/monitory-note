package com.github.modsezam.monitorynote.controller;

import com.github.modsezam.monitorynote.model.ErrorMessage;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorHandlerController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest req, Model model) {
        Object attribute = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (attribute != null ){
            model.addAttribute("errorMessage", "This is http error: " + attribute.toString());
        }
        //do something like logging
        return "error";
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleEntityNotFoundException (EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Unable to find entity: " + exception.getMessage()));

    }

    @Override
    public String getErrorPath() {
        return "/error";
    }



}
