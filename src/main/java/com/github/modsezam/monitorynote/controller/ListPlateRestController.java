package com.github.modsezam.monitorynote.controller;

import com.github.modsezam.monitorynote.model.PlateRecognition;
import com.github.modsezam.monitorynote.service.PlateRecognitionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(path = "/plates")
@AllArgsConstructor
public class ListPlateRestController {

    private final PlateRecognitionService plateRecognitionService;

    @GetMapping("/list")
    public List<PlateRecognition> getListOfNotificationPlates() {

        List<PlateRecognition> plateRecognitions = plateRecognitionService.listTop10PlateRecognitionOrderByIdDesc();
        plateRecognitionService.fillingCompanyNameAndCarAndPersonsIsValid(plateRecognitions);

        return plateRecognitions;
    }

}