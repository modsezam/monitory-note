package com.github.modsezam.monitorynote.repository;

import com.github.modsezam.monitorynote.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository <Person, Long> {
}
