package com.ampla.api.mis.dto;

import com.ampla.api.mis.entities.Employee;
import com.ampla.api.security.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class ResponseEmployeeUser {
    private Employee employeeData;
    private User userData;
}
