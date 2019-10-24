package com.github.modsezam.monitorynote.controller;

import com.github.modsezam.monitorynote.model.Car;
import com.github.modsezam.monitorynote.model.Company;
import com.github.modsezam.monitorynote.model.Person;
import com.github.modsezam.monitorynote.service.CarService;
import com.github.modsezam.monitorynote.service.CompanyService;
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



    @GetMapping ("/list")
    public String listCompanies (Model model) {
        List<Company> companies = companyService.findAll();
        model.addAttribute("companies", companies);
        return "company-list";
    }

    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    @GetMapping ("/addCompany")
    public String addCompany (Model model, Company company) {
        model.addAttribute("company", company);
        return "company-create";
    }

    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    @PostMapping ("/addCompany")
    public String addCompany (Company company) {
        companyService.save(company);
        return "redirect:/company/list";
    }

    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    @GetMapping ("/edit/{idCompany}")
    public String editCompany (Model model,
                               @PathVariable (name = "idCompany") Long idCompany) {
        Company company = companyService.getById(idCompany);
        model.addAttribute("company", company);
        return "company-create";
    }


    @GetMapping ("/details/{id}")
    public String details (Model model,
                           @PathVariable (name = "id") Long id) {
        Company company = companyService.getById(id);
        model.addAttribute("company", company);
        return "company-details";
    }

    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    @GetMapping ("/addPerson/{companyId}")
    public String createPersonInCompany (Model model,
                                         Person person,
                                         @PathVariable (name = "companyId") Long companyId) {
        person.setCompany(companyService.getById(companyId));
        model.addAttribute("person", person);
        return "person-create";
    }

    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    @GetMapping ("/addCar/{companyId}")
    public String createCarInCompany (Model model,
                                         Car car,
                                         @PathVariable (name = "companyId") Long companyId) {
        car.setCompany(companyService.getById(companyId));
        model.addAttribute("car", car);
        return "car-create";
    }


}
