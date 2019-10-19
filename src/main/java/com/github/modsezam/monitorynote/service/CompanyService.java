package com.github.modsezam.monitorynote.service;


import com.github.modsezam.monitorynote.model.Car;
import com.github.modsezam.monitorynote.model.Company;
import com.github.modsezam.monitorynote.repository.CarRepository;
import com.github.modsezam.monitorynote.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class CompanyService {

    private CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Long save(Company company) {
        return companyRepository.save(company).getId();
    }

    public Company getById (Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            return companyOptional.get();
        } else {
            throw new EntityNotFoundException("company, id: " + id);
        }
    }

}
