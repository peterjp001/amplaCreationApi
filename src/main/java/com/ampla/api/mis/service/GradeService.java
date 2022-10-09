package com.ampla.api.mis.service;

import com.ampla.api.exception.DataAlreadyExistException;
import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.entities.Grade;

import java.util.List;

public interface GradeService {

    Grade saveGrade(Grade grade) throws DataAlreadyExistException;

    List<Grade> listGrades();

    Grade updateGrade(Long id, Grade grade) throws DataNotFoundException;

    Grade getGradeById(Long id) throws DataNotFoundException;

    Grade getGradeByGradeName(String gradeName) throws DataNotFoundException;
}

