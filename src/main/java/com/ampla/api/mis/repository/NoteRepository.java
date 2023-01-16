package com.ampla.api.mis.repository;

import com.ampla.api.mis.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    Note findByStudentIdAndAcademicYearIdAndCourseIdAndGradeIdAndEmployeeId(Long studentId, Long ayId, Long courseId, Long gradeId, Long employeeId);

    List<Note> findAllByStudentIdAndAcademicYearId(Long studentId, Long academicYearId);

    List<Note> findAllByAcademicYearId(Long academicYearId);

    List<Note> findAllByAcademicYearIdAndGradeIdAndStudentId(Long academicYearId, Long gradeId, Long studentId);

    List<Note> findAllByAcademicYearIdAndCourseIdAndGradeId(Long academicYearId, Long courseId, Long studentId);
}
