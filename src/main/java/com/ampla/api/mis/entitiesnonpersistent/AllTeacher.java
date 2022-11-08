package com.ampla.api.mis.entitiesnonpersistent;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;


@Data @NoArgsConstructor @AllArgsConstructor
public class AllTeacher {

    @Id
    private Long employee_id;

    private String first_name;

    private String last_name;

    private String code_employee;

    private String nif;

    private String phone;

    private String sexe;

    private String function_name;



}
