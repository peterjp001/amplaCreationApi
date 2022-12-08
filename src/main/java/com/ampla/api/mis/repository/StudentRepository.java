package com.ampla.api.mis.repository;

import com.ampla.api.mis.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {

    Optional<Student> findStudentByCodeStudent(String codeStudent);


}
