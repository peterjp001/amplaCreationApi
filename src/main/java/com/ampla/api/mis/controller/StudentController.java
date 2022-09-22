package com.ampla.api.mis.controller;


import com.ampla.api.mis.entities.Student;
import com.ampla.api.mis.service.StudentService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(path = "/student")
    @PostAuthorize("hasAnyAuthority('USER')")
    public Student newStudent(@RequestBody Student student){
        return studentService.saveStudent(student);
    }

    @GetMapping(path = "/students")
    @PostAuthorize("hasAnyAuthority('USER')")
    public List<Student> listStudent(){
        return studentService.listStudent();
    }

    @GetMapping(path = "/student/{id}")
    @PostAuthorize("hasAnyAuthority('USER')")
    public Optional<Student> getOne(@PathVariable("id") Long id){
        return studentService.getOneStudent(id);
    }

}
