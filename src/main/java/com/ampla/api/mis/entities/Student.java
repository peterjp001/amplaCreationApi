package com.ampla.api.mis.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code_student")
    private String codeStudent;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @JsonFormat(pattern = "yyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(name = "date_birth")
    private Date birthDate;

    @Column(name = "birth_place")
    private String birthPlace;

    @Column(name = "sexe")
    private String sexe;

    @Column(name = "address")
    private String address;

    @Column(name = "father_full_name")
    private String fatherFullName;

    @Column(name = "mother_full_name")
    private String motherFullName;

    @Column(name = "responsible_person")
    private String responsiblePersonFullName;

    @Column(name = "responsible_person_phone")
    private String responsiblePersonPhone;

    @Column(name = "responsible_person_address")
    private String responsiblePersonAddress;

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<StudentRegister> studentRegisters;

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<Note> Notes;

}
