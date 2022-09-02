package com.ampla.api.mis.service;

import com.ampla.api.mis.entities.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student saveStudent(Student student);

    List<Student> listStudent();

    Optional<Student> getOneStudent(Long id);
}
