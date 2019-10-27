package com.github.modsezam.monitorynote.service;

import com.github.modsezam.monitorynote.model.Company;
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

    @Autowired
    private PersonService personService;

    @Autowired
    private CarService carService;

    @Autowired
    private CompanyService companyService;

    public void addPlateNumberToDataBase(PlateRecognition plateRecognition){
        plateRecognitionRepository.save(plateRecognition);
    }

    public List<PlateRecognition> listTop10PlateRecognitionOrderByIdDesc() {
        return plateRecognitionRepository.findTop10ByOrderByIdDesc();
    }

    public void fillingCompanyNameAndCarAndPersonsIsValid(List<PlateRecognition> plateRecognitions) {
        for (PlateRecognition plateRecognition : plateRecognitions) {
            boolean booleanAllPersonsFormCompanyIsValid = personService.checkIsEveryoneFromCompanyHasValidEntry(plateRecognition.getCompanyId());
            log.debug("For company id: {} - checkIsEveryoneFromCompanyHasValidEntry: {}", plateRecognition.getCompanyId(), booleanAllPersonsFormCompanyIsValid);
            plateRecognition.setIsAllPersonsValid(booleanAllPersonsFormCompanyIsValid);

            boolean booleanPlateIsValid = carService.checkIfPlateHasValidEntry(plateRecognition.getPlateNumber());
            log.debug("For plate number: {} - checkIsPlateHasValidEntry: {}", plateRecognition.getPlateNumber(), booleanPlateIsValid);
            plateRecognition.setIsCarValid(booleanPlateIsValid);

            Long companyId = plateRecognition.getCompanyId();
            if (companyId != null){
                Company company = companyService.getById(companyId);
                plateRecognition.setCompanyName(company.getName());
            } else {
                plateRecognition.setCompanyName("-");
            }
        }
    }
}
