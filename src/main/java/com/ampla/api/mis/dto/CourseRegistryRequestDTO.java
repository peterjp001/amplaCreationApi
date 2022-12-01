package com.ampla.api.mis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRegistryRequestDTO {

//    @NotBlank(message = "Required")
    private String courseName;

//    @NotBlank(message = "Required")
    private String codeEmployee;

    private String day;

//    @NotBlank(message = "Required")
    private LocalTime timeStart;

//    @NotBlank(message = "Required")
    private LocalTime timeEnd;
}
