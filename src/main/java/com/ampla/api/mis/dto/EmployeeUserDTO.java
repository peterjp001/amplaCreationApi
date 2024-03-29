package com.ampla.api.mis.dto;

import com.ampla.api.mis.entities.Employee;
import com.ampla.api.mis.entities.Function;
import com.ampla.api.security.entities.Role;
import com.ampla.api.security.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class EmployeeUserDTO implements Serializable {


    //    EMPLOYEE INFORMATIONS

    @NotNull(message = "firstName required.")
    private String firstName;

    @NotBlank(message = "lastName required.")
    private String lastName;

    @NotBlank(message = "sexe required.")
    private String sexe;

    @NotNull(message = "email required")
    @Size(min = 1, max = 100)
    @Email(message = "must be an valid email format")
    private String email;

    @NotBlank(message = "The phone is required.")
    private String phone;

    @NotNull(message = "Birth Date required.")
    private Date birthDate;

    @NotBlank(message = "nif required.")
    private String nif;

    @NotNull(message = "List Functions required")
    private List<Function> functions;

//    USER INFORMATIONS

    @NotBlank(message = "Username required.")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password required.")
    private String password;

    @NotNull(message = "List Roles required")
    private List<Role> roles;
}
