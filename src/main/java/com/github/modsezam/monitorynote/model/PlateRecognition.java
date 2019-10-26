package com.github.modsezam.monitorynote.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlateRecognition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String plateNumber;

    private LocalDateTime plateReadingTime;

    @Formula("select C.id as company_id from (select * from car where registration_nr=plate_number) P join company C on P.id=C.id")
    private Long companyId;

    @Formula("select C.name from (select * from car where registration_nr=plate_number) P join company C on P.company_id=C.id")
    private String companyName;

    public PlateRecognition(String fileName, String plateNumber, LocalDateTime plateReadingTime) {
        this.fileName = fileName;
        this.plateNumber = plateNumber;
        this.plateReadingTime = plateReadingTime;
    }
}
