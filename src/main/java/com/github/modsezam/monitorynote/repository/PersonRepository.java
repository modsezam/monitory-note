package com.github.modsezam.monitorynote.repository;

import com.github.modsezam.monitorynote.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository <Person, Long> {
    boolean existsByIdDocument (String idDocument);


    List<Person> findAllByFirstnameIsLike (String input);
}
