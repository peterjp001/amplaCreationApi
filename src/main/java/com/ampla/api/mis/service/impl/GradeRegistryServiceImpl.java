package com.ampla.api.mis.service.impl;

import com.ampla.api.mis.dto.GradeRegistryDTO;
import com.ampla.api.mis.entities.Grade;
import com.ampla.api.mis.entities.GradeRegistry;
import com.ampla.api.mis.service.GradeRegistryService;
import com.ampla.api.mis.service.GradeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeRegistryServiceImpl implements GradeRegistryService {

    private GradeService gradeService;

    @Override
    public GradeRegistryDTO newGradeRegistry(GradeRegistry gradeRegistry) {

//        Grade curGrade = gradeService.getGradeById(gradeRegistry.)
        return null;
    }

    @Override
    public List<GradeRegistryDTO> listGradeRegistry() {
        return null;
    }

    @Override
    public GradeRegistryDTO updateGradeRegistry(GradeRegistry gradeRegistry) {
        return null;
    }
}
