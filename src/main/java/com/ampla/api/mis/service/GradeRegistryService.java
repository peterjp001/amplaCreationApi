package com.ampla.api.mis.service;

import com.ampla.api.mis.dto.GradeRegistryDTO;
import com.ampla.api.mis.entities.GradeRegistry;

import java.util.List;

public interface GradeRegistryService {

    GradeRegistryDTO newGradeRegistry(GradeRegistry gradeRegistry);

    List<GradeRegistryDTO> listGradeRegistry();

    GradeRegistryDTO updateGradeRegistry(GradeRegistry gradeRegistry);

}
