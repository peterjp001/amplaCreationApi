package com.ampla.api.mis.service;

import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.dto.CourseRegistryRequestDTO;
import com.ampla.api.mis.dto.GradeRegistryRequestDTO;
import com.ampla.api.mis.dto.GradeRegistryResponseDTO;
import com.ampla.api.mis.entities.GradeRegistry;

import java.util.List;

public interface GradeRegistryService {

    void saveGradeRegistry(GradeRegistryRequestDTO gradeRegistry) throws DataNotFoundException;

    List<GradeRegistryResponseDTO> listGradeRegistry();

    GradeRegistry getGradeRegistryById(Long id) throws DataNotFoundException;


    List<GradeRegistry> listGradeRegistryByGradeId( Long id);

    GradeRegistry updateGradeRegistry(Long id, CourseRegistryRequestDTO gradeRegistry) throws DataNotFoundException;

    GradeRegistry getByCourseNameAndCodeEmployee(String courseName, String codeEmployee);

    void deleteGradeRegistry(Long id);

}
