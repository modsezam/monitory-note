package com.github.modsezam.monitorynote.service;


import com.github.modsezam.monitorynote.model.Car;
import com.github.modsezam.monitorynote.model.Person;
import com.github.modsezam.monitorynote.repository.CarRepository;
import com.github.modsezam.monitorynote.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public Long save(Car car) {
        return carRepository.save(car).getId();
    }

    public Car getById (Long id) {
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isPresent()) {
            return carOptional.get();
        } else {
            throw new EntityNotFoundException("car, id: " + id);
        }
    }

    public List<Car> getCarsByRegNumberOrMarkOrModel (String regNo, String mark, String model) {
        return carRepository.findAllByRegistrationNrIsLikeOrMarkIsLikeOrModelIsLike(regNo, mark, model);
    }

}
