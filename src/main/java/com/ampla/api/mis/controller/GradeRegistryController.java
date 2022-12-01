package com.ampla.api.mis.controller;


import com.ampla.api.exception.DataAlreadyExistException;
import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.dto.CourseRegistryRequestDTO;
import com.ampla.api.mis.dto.GradeRegistryRequestDTO;
import com.ampla.api.mis.entities.GradeRegistry;
import com.ampla.api.mis.service.GradeRegistryService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class GradeRegistryController {

    private final GradeRegistryService gradeRegistryService;

    public GradeRegistryController(GradeRegistryService gradeRegistryService) {
        this.gradeRegistryService = gradeRegistryService;
    }


    @PostMapping(path = "/gradeRegistry")
    @PostAuthorize("hasAnyAuthority('USER')")
    public void newGrade(@Valid @RequestBody GradeRegistryRequestDTO grade) throws DataAlreadyExistException, DataNotFoundException {
        System.out.println("ok");
         gradeRegistryService.saveGradeRegistry(grade);
    }

    @GetMapping(path = "/gradeRegistry/{id}")
    @PostAuthorize("hasAnyAuthority('USER')")
    public GradeRegistry gradeRegistryListByGradeId(@PathVariable("id") Long id) throws DataNotFoundException {
        return gradeRegistryService.getGradeRegistryById(id);
    }

    @PutMapping(path = "/gradeRegistry/{id}")
    @PostAuthorize("hasAnyAuthority('USER')")
    public GradeRegistry updateGrade(@PathVariable("id") Long id, @RequestBody CourseRegistryRequestDTO grade) throws DataAlreadyExistException, DataNotFoundException {
        return gradeRegistryService.updateGradeRegistry(id,grade);
    }

}
