package com.github.modsezam.monitorynote.controller;

import com.github.modsezam.monitorynote.model.dto.UserRegistrationRequest;
import com.github.modsezam.monitorynote.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping(path = "/")
public class IndexController {

    @Autowired
    private String myName;

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public String getIndexPage(Model model, Principal principal) {
        model.addAttribute("myName", myName);
//        model.addAttribute("loggedInUser", principal.getName());
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login-form";
    }

    @GetMapping("/register")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public String registrationForm() {
        return "registration-form";
    }

    @PostMapping("/register")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public String register(Model model,
                           @Valid UserRegistrationRequest request,
                           BindingResult bindingResult) {
        if (!request.arePasswordsEqual()) {
            model.addAttribute("errorMessage", "Passwords do not match");
            return "registration-form";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", bindingResult.getFieldError().getDefaultMessage());
            return "registration-form";
        }
        if (!accountService.register(request)) {
            model.addAttribute("errorMessage", "This username is already taken.");
            return "registration-form";
        }
        return "redirect:/admin/user/list";
    }
}