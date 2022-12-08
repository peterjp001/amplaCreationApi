package com.ampla.api.mis.repository;

import com.ampla.api.mis.entities.StudentRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRegisterRepository extends JpaRepository<StudentRegister, Long> {

    List<StudentRegister> findAllByAcademicYearId(Long id);

    StudentRegister findStudentRegisterByIdAndAcademicYearId(Long registerId, Long AcademicYearId);

    Optional<StudentRegister> findStudentRegisterByStudentIdAndAcademicYearId(Long studentId, Long AcademicYearId);
}
