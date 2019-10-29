package com.github.modsezam.monitorynote.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest req, Model model) {
        Object attribute = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (attribute != null ){
            model.addAttribute("errorMessage", "This is http error: " + attribute.toString());
        }
        //do something like logging
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }



}
