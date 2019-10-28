package com.github.modsezam.monitorynote.controller;

import com.github.modsezam.monitorynote.model.NotyficPerson;
import com.github.modsezam.monitorynote.model.Person;
import com.github.modsezam.monitorynote.service.NotyficPersonService;
import com.github.modsezam.monitorynote.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

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

    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    @GetMapping ("/save/{personId}")
    public String createNotyficPerson (Model model, NotyficPerson notyfic,
                                       @PathVariable (name = "personId") Long personId) {
        Person person = personService.getById(personId);
        notyfic.setPerson(person);
        model.addAttribute("notyfic", notyfic);
        return "notyfic-person-create";
    }

    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    @PostMapping("/save")
    public String createNotyficPerson (Model model, NotyficPerson notyficPerson) {
        if (notyficPerson.getStartDateTime().isAfter(notyficPerson.getEndDateTime())) {
            return formError (model, notyficPerson, "End date must by after start date");
        }
        if (notifyCollides(notyficPerson)) {
            return formError(model, notyficPerson, "This person is already notified during this time.");
        }
        notyficPerson.setAccepted(false);
        notyficPersonService.save(notyficPerson);
        return "redirect:/company/details/"+notyficPerson.getPerson().getCompany().getId();
    }



    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    @GetMapping ("/edit/{notyficId}")
    public String editNotyficPerson (Model model,
                              @PathVariable (name = "notyficId") Long notyficId) {
        NotyficPerson notyfic = notyficPersonService.getById(notyficId);
        model.addAttribute("notyfic", notyfic);
        return "notyfic-person-create";
    }

    @PreAuthorize(value = "hasRole('MANAGER')")
    @GetMapping ("/approve/{id}")
    public String approveNotify (@PathVariable (name = "id") Long id) {
       NotyficPerson notyficPerson = notyficPersonService.getById(id);
        notyficPerson.setAccepted(true);
        notyficPersonService.save(notyficPerson);
        return "redirect:/company/details/"+notyficPerson.getPerson().getCompany().getId();
    }

    private String formError(Model model, NotyficPerson notyficPerson, String message) {
        model.addAttribute("errorMessage", message);
        model.addAttribute("notyfic", notyficPerson);
        return "notyfic-person-create";

    }


    private boolean notifyCollides(NotyficPerson notyficPerson) { //TODO refactor
        List<NotyficPerson> notyfics = notyficPerson.getPerson().getNotyfics()
                .stream()
                .filter(n -> n.getId() != notyficPerson.getId())
                .collect(Collectors.toList());

        if (notyfics.isEmpty()) {
            return false;
        }
        for (NotyficPerson existingNotyfic : notyfics) {
            if (!notyficPerson.getStartDateTime().isAfter(existingNotyfic.getEndDateTime()) && !notyficPerson.getEndDateTime().isBefore(existingNotyfic.getStartDateTime())) {
                return true;
            }
        }
        return false;
    }
}
