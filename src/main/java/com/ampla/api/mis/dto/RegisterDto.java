package com.ampla.api.mis.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

    private Long registerId;

    @NotNull(message = "required")
    private Long studentId;

    @NotNull(message = "required")
    private String lastSchool;

    @NotNull(message = "required")
    private String lastGrade;

    @NotNull(message = "required")
    private Long academicYearId;

    @NotNull(message = "required")
    private String gradeName;
}
