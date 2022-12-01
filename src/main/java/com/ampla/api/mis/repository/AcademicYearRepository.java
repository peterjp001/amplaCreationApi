package com.ampla.api.mis.repository;

import com.ampla.api.mis.entities.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear,Long> {
    @Query("select ay from AcademicYear ay order by ay.status asc")
    List<AcademicYear> findAllOrderByStatusAsc();
    AcademicYear findAcademicYearByStatus(String status);
    AcademicYear findAcademicYearByDateStartAndDateEnd(Date dateStart, Date dateEnd);

    AcademicYear findAcademicYearById(Long id);
}
