package com.github.modsezam.monitorynote.controller;

import com.github.modsezam.monitorynote.model.Car;
import com.github.modsezam.monitorynote.model.NotyficCar;
import com.github.modsezam.monitorynote.model.NotyficPerson;
import com.github.modsezam.monitorynote.model.Person;
import com.github.modsezam.monitorynote.service.CarService;
import com.github.modsezam.monitorynote.service.NotyficCarService;
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
@RequestMapping(path = "/notyficCar/")
public class NotyficCarController {

    private NotyficCarService notyficCarService;
    private CarService carService;

    @Autowired
    public NotyficCarController(NotyficCarService notyficCarService, CarService carService) {
        this.notyficCarService = notyficCarService;
        this.carService = carService;
    }

    @GetMapping ("/save/{carId}")
    public String createNotyficCar (Model model, NotyficCar notyfic,
                                       @PathVariable (name = "carId") Long carId) {
        Car car = carService.getById(carId);
        notyfic.setCar(car);
        model.addAttribute("notyfic", notyfic);
        return "notyfic-car-create";
    }


    @PostMapping("/save")
    public String createNotyficCar (NotyficCar notyficCar) {
        notyficCarService.save(notyficCar);
        return "redirect:/company/details/"+notyficCar.getCar().getCompany().getId(); //todo redirect to notify list?
    }

    @GetMapping ("/edit/{notyficId}")
    public String editNotyficCar (Model model,
                              @PathVariable (name = "notyficId") Long notyficId) {
        NotyficCar notyfic = notyficCarService.getById(notyficId);
        model.addAttribute("notyfic", notyfic);
        return "notyfic-car-create";
    }


}
