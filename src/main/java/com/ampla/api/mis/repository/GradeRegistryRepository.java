package com.ampla.api.mis.repository;

import com.ampla.api.mis.entities.GradeRegistry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface GradeRegistryRepository extends JpaRepository<GradeRegistry, Long> {

    List<GradeRegistry> findAllByGradeIdAndAcademicYearId(Long gradeId, Long academicYearId);


    List<GradeRegistry> findGradeRegistriesByGradeId(Long id);

    GradeRegistry findGradeRegistriesByCourseCourseNameAndEmployeeCodeEmployeeAndAcademicYearIdAndTimeStartAndTimeEndAndDay
            (String courseName, String codeEmployee, Long academicYearId, LocalTime timeStart, LocalTime timeEnd, String day);




//    @Query(value = "DELETE FROM grade_registry WHERE id = :id ", nativeQuery = true)
//    void deleteGradeRegistriesByIdNQ(@Param("id") Long id);
}
