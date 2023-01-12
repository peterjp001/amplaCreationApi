package com.ampla.api.mis.dto;

import com.ampla.api.mis.entities.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequestDTO {


    private Long noteId;

    private Float point;

    private Long gradeId;

    private Long courseID;

    private Long employeeId;

    private Long studentId;

    private Long academicYearId;
}
