package com.ampla.api.mis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeCourseUpdateDTO {


    //    @NotBlank(message = "Required")
    private String courseName;

    //    @NotBlank(message = "Required")
    private String codeEmployee;

    //    @NotBlank(message = "Required")
    private LocalTime timeStart;

    //    @NotBlank(message = "Required")
    private LocalTime timeEnd;
}
