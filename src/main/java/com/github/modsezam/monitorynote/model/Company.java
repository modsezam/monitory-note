package com.github.modsezam.monitorynote.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;
    @Column(length = 200)
    private String address;
    @Column(length = 45)
    private String city;
    @Column(length = 25)
    private String postCode;
    @Column(length = 45)
    private String country;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany (mappedBy = "company", fetch = FetchType.EAGER)
    @OrderBy("id")
    private Set<Person> persons = new LinkedHashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany (mappedBy = "company", fetch = FetchType.EAGER)
    @OrderBy ("id")
    private Set<Car> cars = new LinkedHashSet<>();
}
