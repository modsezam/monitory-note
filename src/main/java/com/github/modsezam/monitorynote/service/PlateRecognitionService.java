package com.github.modsezam.monitorynote.service;

import com.github.modsezam.monitorynote.model.PlateRecognition;
import com.github.modsezam.monitorynote.repository.PlateRecognitionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PlateRecognitionService {

    @Autowired
    private PlateRecognitionRepository plateRecognitionRepository;

    public void addPlateNumberToDataBase(PlateRecognition plateRecognition){
        plateRecognitionRepository.save(plateRecognition);
    }

    public List<PlateRecognition> listAllPlateRecognition() {
        return plateRecognitionRepository.findAll();
    }

}
