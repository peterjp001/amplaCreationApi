package com.ampla.api.mis.service.impl;

import com.ampla.api.mis.entities.Student;
import com.ampla.api.mis.repository.StudentRepository;
import com.ampla.api.mis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student saveStudent(Student student)  {

        return studentRepository.save(student);
    }

    @Override
    public List<Student> listStudent() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getOneStudent(Long id) {
        return studentRepository.findById(id);
    }
}
