package com.ampla.api.mis.dto;

import com.ampla.api.mis.entities.AcademicYear;
import com.ampla.api.mis.entities.Grade;
import com.ampla.api.mis.entities.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class RegisterDto {

    private Long registerId;


    private String lastSchool;


    private String lastGrade;


    private Student student;


    private AcademicYear academicYear;


    private Grade grade;
}
