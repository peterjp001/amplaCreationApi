package com.ampla.api.mis.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademicYear implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @NotNull(message = "can not be null")
    @Column(name = "date_start")
    private Date dateStart;

    @JsonFormat(pattern = "yyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @NotNull(message = "can not be null")
    @Column(name = "date_end")
    private Date dateEnd;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "academicYear")
    private List<StudentRegister> register;


}
