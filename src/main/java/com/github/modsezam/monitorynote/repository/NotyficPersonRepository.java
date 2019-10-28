package com.github.modsezam.monitorynote.repository;

import com.github.modsezam.monitorynote.model.NotyficPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface NotyficPersonRepository extends JpaRepository <NotyficPerson, Long> {
    List<NotyficPerson> findAllByAcceptedIsFalse();
    List<NotyficPerson> findAllByAcceptedIsTrue();
}
