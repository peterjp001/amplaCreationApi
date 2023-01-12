package com.ampla.api.mis.service;

import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.dto.NoteRequestDTO;
import com.ampla.api.mis.dto.NoteResponseDTO;
import com.ampla.api.mis.entities.Note;

import java.util.List;

public interface NoteService {

    NoteResponseDTO saveNote(NoteRequestDTO note) throws DataNotFoundException;

    NoteResponseDTO findNoteById(Long id) throws DataNotFoundException;

    List<NoteResponseDTO> findAllByStudentIdAndAcademicYearId(Long studentId, Long academicYearId) throws DataNotFoundException;

    List<NoteResponseDTO> findAllByAcademicYear(Long id) throws DataNotFoundException;

    List<NoteResponseDTO> findAllByAcademicYearAndByGradeAndCourse(Long academicYearId, Long gradeId, Long courseId) throws DataNotFoundException;

    List<NoteResponseDTO> findAllByAcademicYearAndByGradeAndStudent(Long academicYearId, Long gradeId, Long tudentId) throws DataNotFoundException;
}
