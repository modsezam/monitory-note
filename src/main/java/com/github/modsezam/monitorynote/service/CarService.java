package com.github.modsezam.monitorynote.service;


import com.github.modsezam.monitorynote.model.Car;
import com.github.modsezam.monitorynote.model.Person;
import com.github.modsezam.monitorynote.repository.CarRepository;
import com.github.modsezam.monitorynote.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarService {

    private CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Long save(Car car) {
        return carRepository.save(car).getId();
    }

    public Optional<Car> getById (Long id) {
        return carRepository.findById(id);
    }

}
