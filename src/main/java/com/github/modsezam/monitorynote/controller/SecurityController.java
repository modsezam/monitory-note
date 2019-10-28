package com.github.modsezam.monitorynote.controller;

import com.github.modsezam.monitorynote.model.Car;
import com.github.modsezam.monitorynote.model.NotyficCar;
import com.github.modsezam.monitorynote.model.NotyficPerson;
import com.github.modsezam.monitorynote.model.Person;
import com.github.modsezam.monitorynote.repository.CarRepository;
import com.github.modsezam.monitorynote.repository.NotyficCarRepository;
import com.github.modsezam.monitorynote.repository.NotyficPersonRepository;
import com.github.modsezam.monitorynote.repository.PersonRepository;
import com.github.modsezam.monitorynote.service.CarService;
import com.github.modsezam.monitorynote.service.NotyficCarService;
import com.github.modsezam.monitorynote.service.NotyficPersonService;
import com.github.modsezam.monitorynote.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/security")
@PreAuthorize("hasRole('SECURITY')")
public class SecurityController {
    private NotyficPersonService notyficPersonService;
    private NotyficCarService notyficCarService;
    private PersonService personService;
    private CarService carService;

    @Autowired
    public SecurityController(NotyficPersonService notyficPersonService, NotyficCarService notyficCarService, PersonService personService, CarService carService) {
        this.notyficPersonService = notyficPersonService;
        this.notyficCarService = notyficCarService;
        this.personService = personService;
        this.carService = carService;
    }

    @GetMapping("/main")
    public String security(Model model) {
        List<NotyficPerson> approvedPersonNotyfications = notyficPersonService.getApprovedPersonNotyfications();
        List<Person> approvedPersons = approvedPersonNotyfications.stream().map(notyficPerson -> notyficPerson.getPerson()).collect(Collectors.toList());
        List<Person> personsNotApproved = personService.findAll().stream().filter(person -> !approvedPersons.contains(person)).collect(Collectors.toList());

        List<NotyficCar> approvedCarNotyfications = notyficCarService.getApprovedCarNotyfications();
        List<Car> approvedCars = approvedCarNotyfications.stream().map(notyficCar -> notyficCar.getCar()).collect(Collectors.toList());
        List<Car> carsNotApproved = carService.findAll().stream().filter(car -> !approvedCars.contains(car)).collect(Collectors.toList());


        model.addAttribute("approvedPersonNotyfications", approvedPersonNotyfications);
        model.addAttribute("personsNotApproved", personsNotApproved);
        model.addAttribute("approvedCarNotyfications", approvedCarNotyfications);
        model.addAttribute("carsNotApproved", carsNotApproved);


        return "security-main";
    }


}
