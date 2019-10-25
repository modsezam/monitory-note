package com.github.modsezam.monitorynote.service;


import com.github.modsezam.monitorynote.model.Person;
import com.github.modsezam.monitorynote.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
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

    public Person getById (Long id) {
        Optional<Person> personOptional = personRepository.findById(id);
        if (personOptional.isPresent()) {
            return personOptional.get();
        } else {
            throw new EntityNotFoundException("preson, id: " + id);
        }
    }


    public List<Person> searchByNameAndLastname (String input) {
        return personRepository.findAllByFirstnameIsLikeOrLastnameIsLike(input, input);
    }
}
