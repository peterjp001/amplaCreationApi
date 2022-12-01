package com.ampla.api.mis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeRegistryRequestDTO {

    @NotBlank(message = "Required")
    private String gradeName;

    @NotNull(message = "Required")
    private Long academicYearId;

    private List<CourseRegistryRequestDTO> listCourses;

}
