package com.github.modsezam.monitorynote.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
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

    @Formula("(select car.company_id from car where car.registration_nr=plate_number)")
    private Long companyId;

    @Transient
    private String companyName;

    @Transient
    private Boolean isCarValid;

    @Transient
    private Boolean isAllPersonsValid;

    public PlateRecognition(String fileName, String plateNumber, LocalDateTime plateReadingTime) {
        this.fileName = fileName;
        this.plateNumber = plateNumber;
        this.plateReadingTime = plateReadingTime;
    }
}
