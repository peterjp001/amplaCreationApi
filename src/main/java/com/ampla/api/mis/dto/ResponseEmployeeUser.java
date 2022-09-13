package com.ampla.api.mis.dto;

import com.ampla.api.mis.entities.Employee;
import com.ampla.api.security.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@NoArgsConstructor @AllArgsConstructor
public class ResponseEmployeeUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Employee employeeData;
    private User userData;

    public Employee getEmployeeData() {
        return employeeData;
    }

    public void setEmployeeData(Employee employeeData) {
        this.employeeData = employeeData;
    }

    public User getUserData() {
        return userData;
    }

    public void setUserData(User userData) {
        this.userData = userData;
    }
}
