package com.github.modsezam.monitorynote.service;


import com.github.modsezam.monitorynote.model.NotyficCar;
import com.github.modsezam.monitorynote.model.NotyficPerson;
import com.github.modsezam.monitorynote.repository.NotyficCarRepository;
import com.github.modsezam.monitorynote.repository.NotyficPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class NotyficCarService {

    @Autowired
    private NotyficCarRepository notyficCarRepository;

    public Long save(NotyficCar notyficCar) {
        return notyficCarRepository.save(notyficCar).getId();
    }

    public NotyficCar getById (Long id) {
        Optional<NotyficCar> notyficCarOptional = notyficCarRepository.findById(id);
        if (notyficCarOptional.isPresent()) {
            return notyficCarOptional.get();
        } else {
            throw new EntityNotFoundException("notyfication car, id: " + id);
        }
    }


    public List<NotyficCar> getCarNotyficationsToApprove() {
        return notyficCarRepository.findAllByAcceptedIsFalse();
    }
    public List<NotyficCar> getApprovedCarNotyfications() {
        return notyficCarRepository.findAllByAcceptedIsTrue();
    }
}
