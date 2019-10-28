package com.github.modsezam.monitorynote.repository;

import com.github.modsezam.monitorynote.model.NotyficCar;
import com.github.modsezam.monitorynote.model.NotyficPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface NotyficCarRepository extends JpaRepository <NotyficCar, Long> {

    List<NotyficCar> findAllByAcceptedIsFalse();
    List<NotyficCar> findAllByAcceptedIsTrue();

}
