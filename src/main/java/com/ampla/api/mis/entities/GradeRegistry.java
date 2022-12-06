package com.ampla.api.mis.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GradeRegistry implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="day")
    private String day;

    @Column(name = "time_start")
    private LocalTime timeStart;

    @Column(name = "time_end")
    private LocalTime timeEnd;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "academic_year_id")
    private AcademicYear academicYear;

 
  
 
}
