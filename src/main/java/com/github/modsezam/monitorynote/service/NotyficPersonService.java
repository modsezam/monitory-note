package com.github.modsezam.monitorynote.service;


import com.github.modsezam.monitorynote.model.NotyficPerson;
import com.github.modsezam.monitorynote.model.Person;
import com.github.modsezam.monitorynote.repository.NotyficPersonRepository;
import com.github.modsezam.monitorynote.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class NotyficPersonService {

    @Autowired
    private NotyficPersonRepository notyficPersonRepository;

    public Long save(NotyficPerson notyficPerson) {
        return notyficPersonRepository.save(notyficPerson).getId();
    }

    public NotyficPerson getById (Long id) {
        Optional<NotyficPerson> notyficPersonOptional = notyficPersonRepository.findById(id);
        if (notyficPersonOptional.isPresent()) {
            return notyficPersonOptional.get();
        } else {
            throw new EntityNotFoundException("notyfication preson, id: " + id);
        }
    }


    public List<NotyficPerson> getPersonNotyficationsToApprove() {
        return notyficPersonRepository.findAllByAcceptedIsFalse();
    }

    public List<NotyficPerson> getApprovedPersonNotyfications() {
        return notyficPersonRepository.findAllByAcceptedIsTrue();
    }
}
