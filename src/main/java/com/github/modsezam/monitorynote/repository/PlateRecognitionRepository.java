package com.github.modsezam.monitorynote.repository;

import com.github.modsezam.monitorynote.model.PlateRecognition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlateRecognitionRepository extends JpaRepository<PlateRecognition, Long> {
    List<PlateRecognition> findTop10ByOrderByIdDesc();
}
