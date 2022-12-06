package com.ampla.api.mis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NotNull
public class StudentRequestDTO {

    private Long studentId;

    private Long registerId;

    private String codeStudent;

    @NotNull(message = "required")
    private String firstName;

    @NotNull(message = "required")
    private String LastName;

    @NotNull(message = "required")
    private Date birthDate;

    @NotNull(message = "required")
    private String birthPlace;

    @NotNull(message = "required")
    private String sexe;

    @NotNull(message = "required")
    private String address;

    @NotNull(message = "required")
    private String fatherFullName;

    @NotNull(message = "required")
    private String motherFullName;

    @NotNull(message = "required")
    private String responsiblePersonFullName;

    @NotNull(message = "required")
    private String responsiblePersonPhone;

    @NotNull(message = "required")
    private String responsiblePersonAddress;

    @NotNull(message = "required")
    private String lastSchool;

    @NotNull(message = "required")
    private String lastGrade;

    @NotNull(message = "required")
    private String grade;

    @NotNull(message = "required")
    private Long academicYearId;


}
