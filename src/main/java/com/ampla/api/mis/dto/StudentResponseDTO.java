package com.ampla.api.mis.dto;

import com.ampla.api.mis.entities.Student;
import com.ampla.api.mis.entities.StudentRegister;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NotNull
@Builder
public class StudentResponseDTO {


    private Long registerId;

    private Student student;

    private String lastSchool;

   
    private String lastGrade;

   
    private String gradeName;

   
    private Long academicYearId;

    public static StudentResponseDTO fromDto(StudentRegister register){

        return StudentResponseDTO.builder()
//                .student(register.getStudent())
                .registerId(register.getId())
                .lastGrade(register.getLastGrade())
                .lastSchool(register.getLastSchool())
                .gradeName(register.getGrade().getGradeName())
                .academicYearId(register.getAcademicYear().getId())
                .build();
    }


}
