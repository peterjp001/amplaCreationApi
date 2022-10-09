package com.ampla.api.mis.service.impl;

import com.ampla.api.exception.DataAlreadyExistException;
import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.entities.AcademicYear;
import com.ampla.api.mis.repository.AcademicYearRepository;
import com.ampla.api.mis.service.AcademicYearService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AcademicYearServiceImpl implements AcademicYearService {

    private final AcademicYearRepository academicYearRepository;

    public AcademicYearServiceImpl(AcademicYearRepository academicYearRepository) {
        this.academicYearRepository = academicYearRepository;
    }

    @Override
    public AcademicYear saveAcademicYear(AcademicYear academicYear) throws DataAlreadyExistException{
        Optional<AcademicYear> ayByStatus = Optional.ofNullable(academicYearRepository.findAcademicYearByStatus("active"));
        if(ayByStatus.isPresent()){
            throw new DataAlreadyExistException("Une seule année académique peut etre active!");
        }
        Optional<AcademicYear> ayByYears = Optional.ofNullable(academicYearRepository.findAcademicYearByDateStartAndDateEnd(academicYear.getDateStart(), academicYear.getDateEnd()));
        if(ayByYears.isPresent()){
            throw new DataAlreadyExistException("Année académique déjà enregistrée!");
        }
        return academicYearRepository.save(academicYear);
    }

    @Override
    public List<AcademicYear> listAcademicYear() {
        return academicYearRepository.findAllOrderByStatusAsc();
    }

    @Override
    public AcademicYear updateAcademicYear(Long id, AcademicYear academicYear) throws DataNotFoundException {
        Optional<AcademicYear> ay = academicYearRepository.findById(id);
        if(ay.isEmpty()) { throw  new DataNotFoundException("Academic year with id "+ id+" doesn't exist!");
        }

        AcademicYear acy = ay.get();
        if (academicYear.getDateStart() != null) acy.setDateStart(academicYear.getDateStart());
        if (academicYear.getDateEnd() != null) acy.setDateEnd(academicYear.getDateEnd());
        if (academicYear.getStatus() != null) acy.setStatus(academicYear.getStatus());
        System.out.println(acy.getStatus());

        return academicYearRepository.save(acy);
    }
}
