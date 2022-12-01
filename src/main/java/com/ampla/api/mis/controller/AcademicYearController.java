package com.ampla.api.mis.controller;


import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.entities.AcademicYear;
import com.ampla.api.mis.service.AcademicYearService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class AcademicYearController {

    private final AcademicYearService academicYearService;

    public AcademicYearController(AcademicYearService academicYearService) {
        this.academicYearService = academicYearService;
    }

    @PostMapping(path = "/academicyear")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public AcademicYear newAcademicYear(@RequestBody AcademicYear academicYear){
        return academicYearService.saveAcademicYear(academicYear);
    }

    @GetMapping(path="/academicyears")
    @PostAuthorize("hasAnyAuthority('USER')")
    public  List<AcademicYear> getAllAcademicYear(){
        return  academicYearService.listAcademicYear();
    }

    @PutMapping(path = "/academicyear/{id}")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public AcademicYear updateAcademicYear(@PathVariable("id") Long id, @RequestBody AcademicYear academicYear) throws DataNotFoundException {
        return academicYearService.updateAcademicYear(id,academicYear);
    }
}
