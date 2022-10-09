package com.ampla.api.mis.service;


import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.entities.AcademicYear;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AcademicYearService {

    AcademicYear saveAcademicYear(AcademicYear academicYear);
    List<AcademicYear> listAcademicYear();
    AcademicYear updateAcademicYear(Long id, AcademicYear academicYear) throws DataNotFoundException;
}
