package com.ampla.api.mis.service;

import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.entities.Student;
import java.util.List;

public interface StudentService {

    Student saveStudent(Student student) throws DataNotFoundException;

    Student updateStudent(Long id,Student student) throws DataNotFoundException;

    List<Student> listStudent();

    Student getOneStudent(Long id) throws DataNotFoundException;

    Student getStudentByCode(String codeStudent) throws DataNotFoundException;
}
