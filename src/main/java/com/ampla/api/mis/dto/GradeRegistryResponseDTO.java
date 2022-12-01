package com.ampla.api.mis.dto;

import com.ampla.api.mis.entities.AcademicYear;
import com.ampla.api.mis.entities.Course;
import com.ampla.api.mis.entities.Employee;
import org.joda.time.DateTime;


public class GradeRegistryResponseDTO {

    private Long gradeId;

    private Course courseName;

    private Employee employeeName;

    private DateTime timeStart;

    private DateTime timeEnd;

    private AcademicYear academicYear;


}
