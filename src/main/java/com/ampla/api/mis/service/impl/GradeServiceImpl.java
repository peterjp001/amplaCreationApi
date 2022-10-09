package com.ampla.api.mis.service.impl;

import com.ampla.api.exception.DataAlreadyExistException;
import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.entities.Grade;
import com.ampla.api.mis.repository.GradeRepository;
import com.ampla.api.mis.service.GradeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public Grade saveGrade(Grade grade) throws DataAlreadyExistException {
        Optional<Grade> checkGrade = Optional.ofNullable(gradeRepository.findByGradeName(grade.getGradeName()));
        if(checkGrade.isPresent()) throw new DataAlreadyExistException("La classe "+grade.getGradeName()+" existe déjà");
        return gradeRepository.save(grade);
    }

    @Override
    public List<Grade> listGrades() {
        return gradeRepository.findAll();
    }

    @Override
    public Grade updateGrade(Long id, Grade grade) throws DataNotFoundException {
        Grade grd = getGradeById(id);
        if(grade.getGradeName() != null) grd.setGradeName(grade.getGradeName());
        return gradeRepository.save(grd);
    }

    @Override
    public Grade getGradeById(Long id) throws DataNotFoundException {
        Optional<Grade> grd = gradeRepository.findById(id);
        if(grd.isEmpty()) throw new DataNotFoundException("Classe avec l'id "+id+" n'existe pas");
        return grd.get();
    }

    @Override
    public Grade getGradeByGradeName(String gradeName) throws DataNotFoundException {
        Optional<Grade> grd = Optional.ofNullable(gradeRepository.findByGradeName(gradeName));
        if(grd.isEmpty()) throw new DataNotFoundException("la classe "+gradeName+" n'existe pas");
        return grd.get();
    }
}
