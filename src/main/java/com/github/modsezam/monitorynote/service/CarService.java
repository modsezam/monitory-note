package com.github.modsezam.monitorynote.service;


import com.github.modsezam.monitorynote.model.Car;
import com.github.modsezam.monitorynote.model.NotyficCar;
import com.github.modsezam.monitorynote.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

        public Optional<Car> findByPlateNr(String registrationNr) {
        return carRepository.findByRegistrationNr(registrationNr);
    }

    public boolean checkIfPlateHasValidEntry(String plateNumber) {
        Optional<Car> optionalCar = findByPlateNr(plateNumber);
        if (optionalCar.isPresent()){
            Set<NotyficCar> notyfics = optionalCar.get().getNotyfics();
            if (notyfics.size() == 0){
                return false;
            }
            for (NotyficCar notyfic : notyfics) {
                if (notyfic.getStartDateTime().isBefore(LocalDateTime.now())
                        && notyfic.getEndDateTime().isAfter(LocalDateTime.now())
                        && notyfic.isAccepted()){
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public List<Car> findAll () {
        return carRepository.findAll();
    }


}
