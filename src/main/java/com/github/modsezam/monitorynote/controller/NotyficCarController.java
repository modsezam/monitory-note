package com.github.modsezam.monitorynote.controller;

import com.github.modsezam.monitorynote.model.Car;
import com.github.modsezam.monitorynote.model.NotyficCar;
import com.github.modsezam.monitorynote.service.CarService;
import com.github.modsezam.monitorynote.service.NotyficCarService;
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
@RequestMapping(path = "/notyficCar/")
public class NotyficCarController {

    private NotyficCarService notyficCarService;
    private CarService carService;

    @Autowired
    public NotyficCarController(NotyficCarService notyficCarService, CarService carService) {
        this.notyficCarService = notyficCarService;
        this.carService = carService;
    }

    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    @GetMapping ("/save/{carId}")
    public String createNotyficCar (Model model, NotyficCar notyfic,
                                       @PathVariable (name = "carId") Long carId) {
        Car car = carService.getById(carId);
        notyfic.setCar(car);
        model.addAttribute("notyfic", notyfic);
        return "notyfic-car-create";
    }


    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    @PostMapping("/save")
    public String createNotyficCar (Model model, NotyficCar notyficCar) {
        if (notyficCar.getStartDateTime().isAfter(notyficCar.getEndDateTime())) {
            return formError (model, notyficCar, "End date must by after start date");
        }
        if (notifyCollides(notyficCar)) {
            return formError(model, notyficCar, "This car is already notified during this time.");
        }
        notyficCar.setAccepted(false);
        notyficCarService.save(notyficCar);
        return "redirect:/company/details/"+notyficCar.getCar().getCompany().getId();
    }


    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    @GetMapping ("/edit/{notyficId}")
    public String editNotyficCar (Model model,
                              @PathVariable (name = "notyficId") Long notyficId) {
        NotyficCar notyfic = notyficCarService.getById(notyficId);
        model.addAttribute("notyfic", notyfic);
        return "notyfic-car-create";
    }

    @PreAuthorize(value = "hasRole('MANAGER')")
    @GetMapping ("/approve/{id}")
    public String approveNotify (@PathVariable (name = "id") Long id) {
        NotyficCar notyficCar = notyficCarService.getById(id);
        notyficCar.setAccepted(true);
        notyficCarService.save(notyficCar);
        return "redirect:/company/details/"+notyficCar.getCar().getCompany().getId();
    }


    private String formError(Model model, NotyficCar notyficCar, String message) {
        model.addAttribute("errorMessage", message);
        model.addAttribute("notyfic", notyficCar);
        return "notyfic-car-create";
    }

    private boolean notifyCollides(NotyficCar notyficCar) {
        List<NotyficCar> notyfics = notyficCar.getCar().getNotyfics()
                .stream()
                .filter(n -> n.getId() != notyficCar.getId())
                .collect(Collectors.toList());

        if (notyfics.isEmpty()) {
            return false;
        }

        for (NotyficCar existingNotyfic : notyfics) {
            if (!notyficCar.getStartDateTime().isAfter(existingNotyfic.getEndDateTime()) && !notyficCar.getEndDateTime().isBefore(existingNotyfic.getStartDateTime())) {
                return true;
            }
        }
        return false;
    }
}
