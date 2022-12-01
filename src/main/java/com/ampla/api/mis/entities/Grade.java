package com.ampla.api.mis.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Entity
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


    @OneToMany
    @JoinColumn(name = "grade_id")
    private List<GradeRegistry> gradeRegistries;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public List<GradeRegistry> getGradeRegistries() {
        return gradeRegistries;
    }

    public void setGradeRegistries(List<GradeRegistry> gradeRegistries) {
        this.gradeRegistries = gradeRegistries;
    }
}
