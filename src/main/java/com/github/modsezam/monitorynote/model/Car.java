package com.github.modsezam.monitorynote.model;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany (mappedBy = "car", fetch = FetchType.EAGER)
    @OrderBy ("startDateTime")
    private Set<NotyficCar> notyfics = new LinkedHashSet<>();



}
