package com.ampla.api.mis.repository;

import com.ampla.api.mis.entities.GradeRegistry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRegistryRepository extends JpaRepository<GradeRegistry, Long> {
    GradeRegistry findAllByGradeId(Long id);

    List<GradeRegistry> findGradeRegistriesByGradeId(Long id);
}
