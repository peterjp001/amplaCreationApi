package com.ampla.api.mis.controller;


import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.entities.Student;
import com.ampla.api.mis.service.StudentService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {
//
    private final StudentService studentService;
//
//    private final StudentRegisterService studentRegisterService;
//
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

//    @PostMapping(path = "/students")
//    @PostAuthorize("hasAnyAuthority('USER')")
//    public Student newStudent(@RequestBody Student student) throws DataNotFoundException {
//        return studentService.saveStudent(student);
//    }
//
    @GetMapping(path = "/students")
    @PostAuthorize("hasAnyAuthority('USER')")
    public List<Student> listStudent(){
        return studentService.listStudent();
    }
//
    @GetMapping(path = "/students/{id}")
    @PostAuthorize("hasAnyAuthority('USER')")
    public Student getOneStudent(@PathVariable("id") Long id) throws DataNotFoundException {
        return studentService.getOneStudent(id);
    }

    @GetMapping(path = "/students/code/{codeStudent}")
    @PostAuthorize("hasAnyAuthority('USER')")
    public Student getOneStudentByCode(@PathVariable("codeStudent") String codeStudent) throws DataNotFoundException {
        return studentService.getStudentByCode(codeStudent);
    }
//
    @PutMapping(path = "/students/{id}")
    @PostAuthorize("hasAnyAuthority('USER')")
    public Student updateStudent(@PathVariable("id") Long id, @RequestBody Student student) throws DataNotFoundException {
        return studentService.updateStudent(id,student);
    }

}
