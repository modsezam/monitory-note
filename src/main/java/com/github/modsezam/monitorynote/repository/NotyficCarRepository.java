package com.github.modsezam.monitorynote.repository;

import com.github.modsezam.monitorynote.model.NotyficCar;
import com.github.modsezam.monitorynote.model.NotyficPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotyficCarRepository extends JpaRepository <NotyficCar, Long> {
}
