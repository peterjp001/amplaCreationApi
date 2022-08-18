package com.ampla.api.mis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class EmployeeStatusDTO {

    private Long   idEmp;
    private String statusName;
}
