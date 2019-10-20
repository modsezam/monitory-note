package com.github.modsezam.monitorynote.controller;

import com.github.modsezam.monitorynote.model.NotyficPerson;
import com.github.modsezam.monitorynote.model.Person;
import com.github.modsezam.monitorynote.service.NotyficPersonService;
import com.github.modsezam.monitorynote.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/notyficPerson/")
public class NotyficPersonController {

    private NotyficPersonService notyficPersonService;
    private PersonService personService;

    @Autowired
    public NotyficPersonController(NotyficPersonService notyficPersonService, PersonService personService) {
        this.notyficPersonService = notyficPersonService;
        this.personService = personService;
    }

    @GetMapping ("/save/{personId}")
    public String createNotyficPerson (Model model, NotyficPerson notyfic,
                                       @PathVariable (name = "personId") Long personId) {
        Person person = personService.getById(personId);
        notyfic.setPerson(person);
        model.addAttribute("notyfic", notyfic);
        return "notyfic-person-create";
    }


    @PostMapping("/save")
    public String createNotyficPerson (NotyficPerson notyficPerson) {
        notyficPersonService.save(notyficPerson);
        return "redirect:/company/details/"+notyficPerson.getPerson().getCompany().getId(); //todo redirect to notify list?
    }

    @GetMapping ("/edit/{notyficId}")
    public String editNotyficPerson (Model model,
                              @PathVariable (name = "notyficId") Long notyficId) {
        NotyficPerson notyfic = notyficPersonService.getById(notyficId);
        model.addAttribute("notyfic", notyfic);
        return "notyfic-person-create";
    }


}
