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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        List<Company> companies = companyService.search(input);
        List<Person> persons = personService.searchByNameAndLastname(input);
        List<Car> cars = carService.getCarsByRegNumberOrMarkOrModel(input, input, input);



        int records = persons.size() + companies.size() + cars.size();

        if (records == 0) {
            model.addAttribute("errorMessage", "No records found.");
            model.addAttribute("input", input.substring(1, input.length()-1));
            return "search-form";
        }

        model.addAttribute("personsSearched", persons);
        model.addAttribute("carsSearched", cars);
        model.addAttribute("companiesSearched", companies);


//        if (records == 1){
//            Long id = null;
//            if (!companies.isEmpty()) {
//                id = companies.get(0).getId();
//            } else if (!cars.isEmpty()) {
//                id = cars.get(0).getCompany().getId();
//            } else if (!persons.isEmpty()) {
//                id = persons.get(0).getCompany().getId();
//            }
//            return "redirect:/company/details/" + id;
//        }

        Set<Long> companiesIds = new HashSet<>();
        for (Company company : companies) {
            companiesIds.add(company.getId());
        }
        for (Person person : persons) {
            companiesIds.add(person.getCompany().getId());
        }
        for (Car car : cars) {
            companiesIds.add(car.getCompany().getId());
        }

        if (companiesIds.size() == 1) {
            Long companyId = null;
            if (!cars.isEmpty()){
                companyId = cars.get(0).getCompany().getId();
            }
            if (!persons.isEmpty()){
                companyId = persons.get(0).getCompany().getId();
            }
            if (!companies.isEmpty()) {
                companyId = companies.get(0).getId();
            }
            model.addAttribute("company", companyService.getById(companyId));
            return "company-details";
        }

        return "search-result";
    }

}
