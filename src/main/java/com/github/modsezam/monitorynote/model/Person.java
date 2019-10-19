package com.github.modsezam.monitorynote.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45)
    private String firstname;
    @Column(length = 65)
    private String lastname;
    @Column(length = 25)
    private String idDocument;

    @ManyToOne(fetch = FetchType.EAGER)
    private Company company;

//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @OneToMany (mappedBy = "person", fetch = FetchType.EAGER)
//    private Set<NotyficPerson> notifics;

}
