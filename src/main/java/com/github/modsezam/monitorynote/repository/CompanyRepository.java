package com.github.modsezam.monitorynote.repository;

import com.github.modsezam.monitorynote.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository <Company, Long> {
    boolean existsByName(String name);
}
