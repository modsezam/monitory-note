package com.github.modsezam.monitorynote.controller;

import com.github.modsezam.monitorynote.model.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@ControllerAdvice
public class ErrorHandlerController implements ErrorController {

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public String handleEntityNotFoundException (EntityNotFoundException exception, Model model) {
        String errorMessage = "Unable to find entity: " + exception.getMessage();
        model.addAttribute("errorMessage", errorMessage);
        log.debug(errorMessage);
        return "error";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest req, Model model) {
        Object attribute = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (attribute != null ){
            model.addAttribute("errorMessage", "This is http error: " + attribute.toString());
            log.debug("Error: " + attribute.toString());
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
