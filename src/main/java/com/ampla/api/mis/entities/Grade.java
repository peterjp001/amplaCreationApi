package com.ampla.api.mis.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

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

    @JsonIgnore
    @OneToMany(mappedBy="grade",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<GradeRegistry> gradeRegistries = new ArrayList<>();

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

    public Collection<GradeRegistry> getGradeRegistries() {
        return gradeRegistries;
    }

    public void setGradeRegistries(Collection<GradeRegistry> gradeRegistries) {
        this.gradeRegistries = gradeRegistries;
    }
}
