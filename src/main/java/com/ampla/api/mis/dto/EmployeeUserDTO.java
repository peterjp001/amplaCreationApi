package com.ampla.api.mis.dto;

import com.ampla.api.mis.entities.Function;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor(staticName = "build")
public class EmployeeUserDTO implements Serializable {

    //    Employee Informations
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

    @NotBlank(message = "Birth Date required.")
    private Date birthDate;

    @NotBlank(message = "nif required.")
    private String nif;

    private List<Function> function;

//    User Information
}
