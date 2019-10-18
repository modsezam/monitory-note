package com.github.modsezam.monitorynote.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (length = 25)
    private String registrationNr;
    @Column (length = 45)
    private String mark;
    @Column (length = 45)
    private String model;

    @ManyToOne (fetch = FetchType.EAGER)
    private Company company;
}
