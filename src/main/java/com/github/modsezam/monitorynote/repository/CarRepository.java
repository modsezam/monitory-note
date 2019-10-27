package com.github.modsezam.monitorynote.repository;

import com.github.modsezam.monitorynote.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository <Car, Long> {

    boolean existsByRegistrationNr (String registrationNr);
    List<Car> findAllByRegistrationNrIsLikeOrMarkIsLikeOrModelIsLike (String regNo, String mark, String model);
    Optional<Car> findByRegistrationNr(String registrationNr);

}
