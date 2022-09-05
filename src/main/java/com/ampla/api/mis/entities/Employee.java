package com.ampla.api.mis.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.ampla.api.security.entities.User;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fist_name")
    @NotNull(message = "firstName required.")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "lastName required.")
    private String lastName;

    @Column(name = "sexe")
    @NotBlank(message = "sexe required.")
    private String sexe;

    @Column(name = "email")
    @NotNull(message = "email required")
    @Size(min = 1, max = 100)
    @Email(message = "must be an valid email format")
    private String email;

    @Column(name = "phone")
    @NotBlank(message = "The phone is required.")
    private String phone;

    @Column(name = "date_birth")
    @NotNull(message = "Birth Date required.")
    private Date birthDate;

    @Column(name = "nif")
    @NotBlank(message = "nif required.")
    private String nif;

    @ManyToMany(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
    private Collection<Function> functions = new ArrayList<>();

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Course> course = new ArrayList<>();


}
