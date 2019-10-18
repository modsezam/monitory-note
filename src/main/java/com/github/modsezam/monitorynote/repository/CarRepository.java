package com.github.modsezam.monitorynote.repository;

import com.github.modsezam.monitorynote.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository <Car, Long> {
}
