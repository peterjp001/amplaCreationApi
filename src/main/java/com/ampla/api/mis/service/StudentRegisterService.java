package com.ampla.api.mis.service;

import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.dto.StudentRequestDTO;
import com.ampla.api.mis.dto.StudentResponseDTO;
import com.ampla.api.mis.entities.GradeRegistry;
import com.ampla.api.mis.entities.StudentRegister;

import java.util.List;

public interface StudentRegisterService {

    StudentResponseDTO saveStudentRegister(StudentRequestDTO dto) throws DataNotFoundException;

    List<StudentResponseDTO> listAllStudentRegisterByAY(Long id);

    StudentRegister updateStudentRegister(Long registerId, StudentResponseDTO register) throws DataNotFoundException;

    StudentRegister getStudentRegisterById(Long id) throws DataNotFoundException;

    void deleteStudentRegister(Long id);

    StudentResponseDTO getGradeRegistryByStudentIdAndAcademicYear(Long studentId, Long academicYearId) throws DataNotFoundException;
    StudentResponseDTO getStudentByIdAndAcademicYear(Long studentId, Long academicYearId);
}
