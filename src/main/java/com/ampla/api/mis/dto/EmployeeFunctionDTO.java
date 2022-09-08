package com.ampla.api.mis.dto;

import com.ampla.api.mis.entities.Function;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
public class EmployeeFunctionDTO {


    //    Employee Informations 
    @NotBlank(message = "Code Employee Required.")
    private String codeEmployee;

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

}
