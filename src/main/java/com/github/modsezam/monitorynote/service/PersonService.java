package com.github.modsezam.monitorynote.service;

import com.github.modsezam.monitorynote.model.Company;
import com.github.modsezam.monitorynote.model.NotyficPerson;
import com.github.modsezam.monitorynote.model.Person;
import com.github.modsezam.monitorynote.repository.CompanyRepository;
import com.github.modsezam.monitorynote.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CompanyRepository companyRepository;

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

    public boolean checkIsEveryoneFromCompanyHasValidEntry(Long companyId) {
        if (companyId != null) {
            Optional<Company> companyOptional = companyRepository.findById(companyId);
            if (companyOptional.isPresent()) {
                List<Person> personList = personRepository.findByCompany(companyOptional.get());
                for (Person person : personList) {
                    Optional<NotyficPerson> currentNotification = findCurrentNotification(person.getNotyfics());
                    if (currentNotification.isPresent()) {
                        boolean approved = currentNotification.get().isApproved();
                        if (!approved) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    private Optional<NotyficPerson> findCurrentNotification(Set<NotyficPerson> notyficPersons) {
        //method to fix - return one result in scope of date but not check is approved
        return notyficPersons.stream()
                .filter(notyficPerson -> notyficPerson.getStartDateTime().isBefore(LocalDateTime.now())
                        && notyficPerson.getEndDateTime().isAfter(LocalDateTime.now()))
                .findFirst();
    }
}
