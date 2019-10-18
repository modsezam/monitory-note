package com.github.modsezam.monitorynote.service;


import com.github.modsezam.monitorynote.model.Person;
import com.github.modsezam.monitorynote.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Long save(Person person) {
        return personRepository.save(person).getId();
    }

    public Optional<Person> getById (Long id) {
        return personRepository.findById(id);
    }


}