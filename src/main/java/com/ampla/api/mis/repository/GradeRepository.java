package com.ampla.api.mis.repository;


import com.ampla.api.mis.entities.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade,Long> {

    Grade findByGradeName(String gradeName);
}
