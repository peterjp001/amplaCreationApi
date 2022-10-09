package com.ampla.api.mis.controller;


import com.ampla.api.exception.DataAlreadyExistException;
import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.entities.Grade;
import com.ampla.api.mis.service.GradeService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping(path = "/grade")
    @PostAuthorize("hasAnyAuthority('USER')")
    public Grade newGrade(@Valid @RequestBody Grade grade) throws DataAlreadyExistException {
        return gradeService.saveGrade(grade);
    }

    @GetMapping(path = "/grades")
    @PostAuthorize("hasAnyAuthority('USER')")
    public List<Grade> gradeList(){
        return gradeService.listGrades();
    }

    @GetMapping(path = "/grade/{id}")
    @PostAuthorize("hasAnyAuthority('USER')")
    public Grade gradeById(@PathVariable("id") Long id) throws DataNotFoundException {
        return gradeService.getGradeById(id);
    }

    @PutMapping(path = "/grade/{id}")
    @PostAuthorize("hasAnyAuthority('USER')")
    public Grade updateGrade(@PathVariable("id") Long id, @RequestBody Grade grade) throws DataNotFoundException {
        return gradeService.updateGrade(id,grade);
    }



}
