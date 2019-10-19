package com.github.modsezam.monitorynote.controller;

import com.github.modsezam.monitorynote.model.Car;
import com.github.modsezam.monitorynote.model.Person;
import com.github.modsezam.monitorynote.service.CarService;
import com.github.modsezam.monitorynote.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/car/")
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/save")
    public String createCar (Car car) {
        carService.save(car);
        return "redirect:/company/details/"+car.getCompany().getId();
    }

    @GetMapping ("/edit/{carId}")
    public String editCar (Model model,
                              @PathVariable (name = "carId") Long carId) {
        Car car = carService.getById(carId);
        model.addAttribute("car", car);
        return "car-create";
    }


}
