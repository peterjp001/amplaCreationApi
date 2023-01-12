package com.ampla.api.mis.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grade_name")
    @NotBlank(message = "GradeName is required!")
    private String gradeName;


    @JsonIgnore
    @OneToMany(mappedBy = "grade")
    private List<GradeRegistry> gradeRegistries;

    @JsonIgnore
    @OneToMany(mappedBy = "grade")
    private List<StudentRegister> register;

    @JsonIgnore
    @OneToMany(mappedBy = "grade")
    private List<Note> notes;
}
