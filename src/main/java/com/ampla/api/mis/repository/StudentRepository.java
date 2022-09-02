package com.ampla.api.mis.repository;

import com.ampla.api.mis.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {

}
