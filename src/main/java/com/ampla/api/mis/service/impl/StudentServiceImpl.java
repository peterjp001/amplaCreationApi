package com.ampla.api.mis.service.impl;

import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.entities.Student;
import com.ampla.api.mis.repository.StudentRepository;
import com.ampla.api.mis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    public Student updateStudent(Long id, Student student) throws DataNotFoundException {

        Student updateStudent = getOneStudent(id);

        if(student != null){
            if(student.getFirstName() != null){
                updateStudent.setFirstName(student.getFirstName());
            }
            if(student.getLastName() != null){
            updateStudent.setLastName(student.getLastName());
            }
            if(student.getBirthDate() != null){
            updateStudent.setBirthDate(student.getBirthDate());
            }
            if(student.getBirthPlace() != null){
            updateStudent.setBirthPlace(student.getBirthPlace());
            }
            if(student.getSexe() != null){
            updateStudent.setSexe(student.getSexe());
            }
            if(student.getAddress() != null){
            updateStudent.setAddress(student.getAddress());
            }
            if(student.getFatherFullName() != null){
            updateStudent.setFatherFullName(student.getFatherFullName());
            }
            if(student.getMotherFullName() != null){
            updateStudent.setMotherFullName(student.getMotherFullName());
            }
            if(student.getResponsiblePersonAddress() != null){
            updateStudent.setResponsiblePersonAddress(student.getResponsiblePersonAddress());
            }
            if(student.getResponsiblePersonPhone() != null){
            updateStudent.setResponsiblePersonPhone(student.getResponsiblePersonPhone());
            }
            if(student.getResponsiblePersonFullName() != null){
            updateStudent.setResponsiblePersonFullName(student.getResponsiblePersonFullName());
            }


        }
        return saveStudent(updateStudent);
    }

    @Override
    public List<Student> listStudent() {
        return studentRepository.findAll();
    }

    @Override
    public Student getOneStudent(Long id) throws DataNotFoundException {
        return studentRepository.findById(id).orElseThrow(
                ()->new DataNotFoundException("Student With ID "+id+" not exist"));
    }

    @Override
    public Student getByFirstNameAndLastName(String firstName, String lastName) throws DataNotFoundException {
        return studentRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(()->new DataNotFoundException("Student '"+firstName+" "+lastName+"' not found"));
    }

    @Override
    public Student getStudentByCode(String codeStudent) throws DataNotFoundException {
        return  studentRepository.findStudentByCodeStudent(codeStudent).orElseThrow(
                ()->new DataNotFoundException("L'élève avec le Code '"+codeStudent+"' n'existe pas"));
    }
}
