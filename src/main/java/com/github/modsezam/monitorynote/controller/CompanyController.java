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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping (path = "/company/")
public class CompanyController {

    private CompanyService companyService;
    private CarService carService;
    private PersonService personService;

    @Autowired
    public CompanyController(CompanyService companyService, CarService carService, PersonService personService) {
        this.companyService = companyService;
        this.carService = carService;
        this.personService = personService;
    }

    @GetMapping ("/details/{id}")
    public String details (Model model,
                           @PathVariable (name = "id") Long id) {
        Company company = companyService.getById(id);
        model.addAttribute("company", company);
        return "company-details";
    }

    @GetMapping ("/addPerson/{companyId}")
    public String createPersonInCompany (Model model,
                                         Person person,
                                         @PathVariable (name = "companyId") Long companyId) {
        person.setCompany(companyService.getById(companyId));
        model.addAttribute("person", person);
        return "person-create";
    }

    @GetMapping ("/addCar/{companyId}")
    public String createCarInCompany (Model model,
                                         Car car,
                                         @PathVariable (name = "companyId") Long companyId) {
        car.setCompany(companyService.getById(companyId));
        model.addAttribute("car", car);
        return "car-create";
    }


}
