package com.github.modsezam.monitorynote.controller;

import com.github.modsezam.monitorynote.model.Person;
import com.github.modsezam.monitorynote.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/person/")
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    @PostMapping("/save")
    public String createPerson (Person person) {
        personService.save(person);
        return "redirect:/company/details/"+person.getCompany().getId();
    }

    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    @GetMapping ("/edit/{personId}")
    public String editPerson (Model model,
                              @PathVariable (name = "personId") Long personId) {
        Person person = personService.getById(personId);
        model.addAttribute("person", person);
        return "person-create";
    }

}
