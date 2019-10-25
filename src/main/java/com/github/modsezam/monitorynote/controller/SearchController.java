package com.github.modsezam.monitorynote.controller;

import com.github.modsezam.monitorynote.model.Car;
import com.github.modsezam.monitorynote.model.Company;
import com.github.modsezam.monitorynote.model.Person;
import com.github.modsezam.monitorynote.service.CarService;
import com.github.modsezam.monitorynote.service.CompanyService;
import com.github.modsezam.monitorynote.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    PersonService personService;
    CompanyService companyService;
    CarService carService;

    @Autowired
    public SearchController(PersonService personService, CompanyService companyService, CarService carService) {
        this.personService = personService;
        this.companyService = companyService;
        this.carService = carService;
    }

    @GetMapping ("/search")
    public String search () {
        return "search-form";
    }

    @PostMapping ("/search")
    public String search (String input, Model model) {
        input = "%"+input+"%";
        List<Person> personList = personService.search(input);


        List<Car> cars = new ArrayList<>();
        List<Company> companies = new ArrayList<>();







        model.addAttribute("persons", personList);
        model.addAttribute("cars", cars);
        model.addAttribute("companies", companies);

        return "search-result";
    }





}
