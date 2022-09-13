package com.ampla.api.mis.service.impl;

import com.ampla.api.mis.entitiesnonpersistent.AllTeacher;
import com.ampla.api.mis.repository.AllTeacherRepository;
import com.ampla.api.mis.service.AllTeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AllTeacherServiceImpl implements AllTeacherService {

    private final AllTeacherRepository atRepo;

    public AllTeacherServiceImpl(AllTeacherRepository atRepo) {
        this.atRepo = atRepo;
    }

    @Override
    public List<AllTeacher> listTeachers() {
        return atRepo.findAll();
    }
}
